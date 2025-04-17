package com.info.api.util;

import com.info.api.constants.Constants;
import com.info.api.dto.ic.ICBankDetailsDTO;
import com.info.api.dto.ic.ICBeneficiaryDTO;
import com.info.api.dto.ic.ICOutstandingTransactionDTO;
import com.info.api.entity.Branch;
import com.info.api.entity.MbkBrn;
import com.info.api.entity.RemittanceData;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RemittanceValidator {

    private RemittanceValidator() {
    }

    public static void validateOutstandingRemittanceData(RemittanceData remittanceData, Map<Integer, Branch> branchMap, Map<String, MbkBrn> mbkBrnMap) {
        if (!remittanceData.isDuplicate()) {
            String digits = "\\d*";
            Predicate<String> isInValid = routing -> isInValidateRouting(routing)
                    || !isValidRouting(branchMap.get(Integer.parseInt(routing.substring(3))), mbkBrnMap.get(routing.substring(3)), routing.substring(0, 3), routing.substring(3), remittanceData.getRemittanceMessageType());
            if (isInValid.test(remittanceData.getBranchRoutingNumber())) {
                remittanceData.setProcessStatus(RemittanceData.REJECTED);
                remittanceData.setStopPayReason(Constants.WRONG_ROUTING_OR_WRONG_BRANCH);
            }
            if (!remittanceData.getProcessStatus().equals(RemittanceData.REJECTED)) {
                String characters = "^[a-zA-Z.-]+$";
                String creditorName = remittanceData.getCreditorName().replace(" ", "");
                Predicate<String> eftAccountNumberValid = accountNumber -> !accountNumber.matches(digits) && remittanceData.getRemittanceMessageType().equals(RemittanceData.EFT) && accountNumber.length() != 13;
                Predicate<String> beftnAccountNumberValid = accountNumber -> !accountNumber.matches(digits) && remittanceData.getRemittanceMessageType().equals(RemittanceData.BEFTN) && accountNumber.length() > 17;

                if (eftAccountNumberValid.or(beftnAccountNumberValid).test(remittanceData.getCreditorAccountNo())) {
                    remittanceData.setProcessStatus(RemittanceData.REJECTED);
                    remittanceData.setStopPayReason(Constants.ACCOUNT_NUMBER_INVALID);
                } else if (Objects.isNull(remittanceData.getCreditorName()) || remittanceData.getCreditorName().isEmpty() || !creditorName.matches(characters)) {
                    remittanceData.setProcessStatus(RemittanceData.REJECTED);
                    remittanceData.setStopPayReason(Constants.ACCOUNT_NAME_IS_EMPTY);
                }
            }
        }
    }

    public static List<String> getBranchRoutingNumbers(List<ICOutstandingTransactionDTO> transactionDTOArrayList) {
        return transactionDTOArrayList.stream().map(ICOutstandingTransactionDTO::getBeneficiary).filter(Objects::nonNull)
                .map(ICBeneficiaryDTO::getBankDetails).filter(Objects::nonNull).map(ICBankDetailsDTO::getBankCode)
                .filter(RemittanceValidator::routingMoreThanThreeDigits).map(b -> b.substring(3)).distinct().collect(Collectors.toList());
    }

    public static boolean routingMoreThanThreeDigits(String routingNumber) {
        String digits = "\\d*";
        return Objects.nonNull(routingNumber) && routingNumber.matches(digits) && routingNumber.length() > 3 && routingNumber.substring(3).length() == 6;
    }

    public static boolean isMbkBrnBranchRoutingExist(MbkBrn mbkBrn, String bankCode, String routingNumber) {
        return mbkBrn.getMbkbrnKey().getBankCode().equals(bankCode) && mbkBrn.getMbkbrnKey().getBranchRouting().equals(routingNumber);
    }

    public static boolean isMbkBrnBranchRoutingExist(List<MbkBrn> mbkBrnList, String bankCode, String routingNumber) {
        Predicate<MbkBrn> isSelectedBranch = mbkBrn -> mbkBrn.getMbkbrnKey().getBankCode().equals(bankCode) && mbkBrn.getMbkbrnKey().getBranchRouting().equals(routingNumber);
        return mbkBrnList.stream().filter(isSelectedBranch).count() > 0;
    }

    public static boolean isBranchRoutingExist(Branch branch, String routingNumber) {
        return branch.getRoutingNumber().equals(Integer.parseInt(routingNumber));
    }

    public static boolean isBranchRoutingExist(List<Branch> branchList, String routingNumber) {
        Predicate<Branch> isSelectedBranch = branch -> branch.getRoutingNumber().equals(Integer.parseInt(routingNumber));
        return branchList.stream().filter(isSelectedBranch).count() > 0;
    }

    public static boolean isValidRouting(Branch branch, MbkBrn mbkBrn, String bankCode, String routingNumber, String messageType) {
        if (RemittanceData.EFT.equals(messageType)) {
            return isBranchRoutingExist(branch, routingNumber);
        } else if (RemittanceData.BEFTN.equals(messageType)) {
            return isMbkBrnBranchRoutingExist(mbkBrn, bankCode, routingNumber);
        }
        return false;
    }

    public static boolean isValidRouting(List<Branch> branchList, List<MbkBrn> mbkBrnList, String bankCode, String routingNumber, String messageType) {
        if (messageType.equals(RemittanceData.EFT)) {
            return isBranchRoutingExist(branchList, routingNumber);
        } else if (messageType.equals(RemittanceData.BEFTN)) {
            return isMbkBrnBranchRoutingExist(mbkBrnList, bankCode, routingNumber);
        }
        return false;
    }


    public static boolean isEFTRemittance(String rmBankCode, String bankCode) {
        return Objects.nonNull(rmBankCode) && rmBankCode.trim().length() >= 3 && rmBankCode.trim().substring(0, 3).equals(bankCode);
    }

    public static boolean isInValidateRouting(String routingNumber) {
        String digits = "\\d*";
        return Objects.isNull(routingNumber) || routingNumber.isEmpty() || !routingNumber.matches(digits) || routingNumber.length() != 9;
    }

}
