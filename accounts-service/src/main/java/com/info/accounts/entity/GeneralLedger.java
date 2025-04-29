package com.info.accounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GENERAL_LEDGER")
public class GeneralLedger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "EXTGL_ACCESS_CODE")
    private String extglAccessCode;

    @Column(name = "EXTGL_EXT_HEAD_DESCN")
    private String extglExtHeadDescn;

    @Column(name = "EXTGL_CONC_DESCN")
    private String extglConcDescn;

    @Column(name = "EXTGL_GL_HEAD")
    private Integer extglGLHead;

    @Column(name = "EXTGL_SUB_GL_HEAD")
    private Integer extglSubGLHead;

    @Column(name = "EXTGL_BRK_GL_HEAD")
    private Integer extglBrkGLHEad;

    @Column(name = "EXTGL_GL_USAGE")
    private String extglGLUsage;

    @Override
    public String toString() {
        return "GeneralLedger [extglAccessCode=" + extglAccessCode + ", extglExtHeadDescn=" + extglExtHeadDescn
                + ", extglConcDescn=" + extglConcDescn + ", extglGLHead=" + extglGLHead + ", extglSubGLHead="
                + extglSubGLHead + ", extglBrkGLHEad=" + extglBrkGLHEad + ", extglGLUsage=" + extglGLUsage + "]";
    }

}
