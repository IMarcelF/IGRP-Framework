<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" omit-xml-declaration="yes" encoding="ISO-8859-1" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
    <xsl:template match="/">
        <html>
            <head>
                <xsl:call-template name="IGRP-head"/>
                <!-- FORM CSS INCLUDES -->
                <link rel="stylesheet" type="text/css" href="{$path}/core/igrp/form/igrp.forms.css"/>
                <!-- TOOLSBAR CSS INCLUDES -->
                <link rel="stylesheet" type="text/css" href="{$path}/core/igrp/toolsbar/toolsbar.css"/>
                <!-- SELECT CSS INCLUDES -->
                <link rel="stylesheet" type="text/css" href="{$path}/plugins/select2/select2.min.css"/>
                <link rel="stylesheet" type="text/css" href="{$path}/plugins/select2/select2.style.css"/>
                <style/>
            </head>
            <body class="{$bodyClass} sidebar-off">
                <xsl:call-template name="IGRP-topmenu"/>
                <form method="POST" class="IGRP-form" name="formular_default" enctype="multipart/form-data">
                    <div class="container-fluid">
                        <div class="row">
                            <xsl:call-template name="IGRP-sidebar"/>
                            <div class="col-sm-9 col-md-10 col-md-offset-2 col-sm-offset-3 main" id="igrp-contents">
                                <div class="content">
                                    <div class="row">
                                        <div class="gen-column col-md-12">
                                            <div class="gen-inner">
                                                <xsl:apply-templates mode="igrp-messages" select="rows/content/messages"/>
                                                <div class="box igrp-box-holder gen-container-item " gen-class="" item-name="box_1">
                                                    <div class="box-body" gen-preserve-content="true">
                                                        <div>
                                                            <div class="row">
                                                                <div class="gen-column col-sm-6">
                                                                    <div class="gen-inner">
                                                                        <xsl:if test="rows/content/sectionheader_1">
                                                                            <section class="content-header gen-container-item " gen-class="" item-name="sectionheader_1">
                                                                                <h2>
                                                                                    <xsl:value-of select="rows/content/sectionheader_1/fields/sectionheader_1_text/value"/>
                                                                                </h2>
                                                                            </section>
                                                                        </xsl:if>
                                                                    </div>
                                                                </div>
                                                                <div class="gen-column col-sm-6">
                                                                    <div class="gen-inner">
                                                                        <xsl:if test="rows/content/toolsbar_1">
                                                                            <div class="toolsbar-holder default gen-container-item " gen-structure="toolsbar" gen-fields=".btns-holder a.btn" gen-class="" item-name="toolsbar_1">
                                                                                <div class="btns-holder   pull-right" role="group">
                                                                                    <xsl:apply-templates select="rows/content/toolsbar_1" mode="gen-buttons">
                                                                                        <xsl:with-param name="vertical" select="'true'"/>
                                                                                    </xsl:apply-templates>
                                                                                </div>
                                                                            </div>
                                                                        </xsl:if>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <xsl:if test="rows/content/form_1">
                                                    <div class="box igrp-forms gen-container-item " gen-class="" item-name="form_1">
                                                        <div class="box-body">
                                                            <div role="form">
                                                                <xsl:apply-templates mode="form-hidden-fields" select="rows/content/form_1/fields"/>
                                                                <xsl:if test="rows/content/form_1/fields/balcao">
                                                                    <div class="col-md-3 form-group  gen-fields-holder" item-name="balcao" item-type="select" required="required">
                                                                        <label for="{rows/content/form_1/fields/balcao/@name}">
                                                                            <xsl:value-of select="rows/content/form_1/fields/balcao/label"/>
                                                                        </label>
                                                                        <select class="form-control select2 " id="form_1_balcao" name="{rows/content/form_1/fields/balcao/@name}" required="required">
                                                                            <xsl:for-each select="rows/content/form_1/fields/balcao/list/option">
                                                                                <option value="{value}" label="{text}">
                                                                                    <xsl:if test="@selected='true'">
                                                                                        <xsl:attribute name="selected">selected</xsl:attribute>
                                                                                    </xsl:if>
                                                                                    <span>
                                                                                        <xsl:value-of select="text"/>
                                                                                    </span>
                                                                                </option>
                                                                            </xsl:for-each>
                                                                        </select>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/localizacao">
                                                                    <div class="form-group col-md-3   gen-fields-holder" item-name="localizacao" item-type="text" required="required">
                                                                        <label for="{rows/content/form_1/fields/localizacao/@name}">
                                                                            <span>
                                                                                <xsl:value-of select="rows/content/form_1/fields/localizacao/label"/>
                                                                            </span>
                                                                        </label>
                                                                        <input type="text" value="{rows/content/form_1/fields/localizacao/value}" class="form-control " id="{rows/content/form_1/fields/localizacao/@name}" name="{rows/content/form_1/fields/localizacao/@name}" readonly="readonly" required="required" maxlength="30" placeholder=""></input>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/fuso_horario">
                                                                    <div class="form-group col-md-3   gen-fields-holder" item-name="fuso_horario" item-type="text" required="required">
                                                                        <label for="{rows/content/form_1/fields/fuso_horario/@name}">
                                                                            <span>
                                                                                <xsl:value-of select="rows/content/form_1/fields/fuso_horario/label"/>
                                                                            </span>
                                                                        </label>
                                                                        <input type="text" value="{rows/content/form_1/fields/fuso_horario/value}" class="form-control " id="{rows/content/form_1/fields/fuso_horario/@name}" name="{rows/content/form_1/fields/fuso_horario/@name}" readonly="readonly" required="required" maxlength="30" placeholder=""></input>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/escolher_hora">
                                                                    <div class="col-md-3 form-group  gen-fields-holder" item-name="escolher_hora" item-type="select" required="required">
                                                                        <label for="{rows/content/form_1/fields/escolher_hora/@name}">
                                                                            <xsl:value-of select="rows/content/form_1/fields/escolher_hora/label"/>
                                                                        </label>
                                                                        <select class="form-control select2 " id="form_1_escolher_hora" name="{rows/content/form_1/fields/escolher_hora/@name}" required="required">
                                                                            <xsl:for-each select="rows/content/form_1/fields/escolher_hora/list/option">
                                                                                <option value="{value}" label="{text}">
                                                                                    <xsl:if test="@selected='true'">
                                                                                        <xsl:attribute name="selected">selected</xsl:attribute>
                                                                                    </xsl:if>
                                                                                    <span>
                                                                                        <xsl:value-of select="text"/>
                                                                                    </span>
                                                                                </option>
                                                                            </xsl:for-each>
                                                                        </select>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/nr_de_servicos_por_agendamento">
                                                                    <div class="col-md-3 form-group  gen-fields-holder" item-name="nr_de_servicos_por_agendamento" item-type="select" required="required">
                                                                        <label for="{rows/content/form_1/fields/nr_de_servicos_por_agendamento/@name}">
                                                                            <xsl:value-of select="rows/content/form_1/fields/nr_de_servicos_por_agendamento/label"/>
                                                                        </label>
                                                                        <select class="form-control select2 " id="form_1_nr_de_servicos_por_agendamento" name="{rows/content/form_1/fields/nr_de_servicos_por_agendamento/@name}" required="required">
                                                                            <xsl:for-each select="rows/content/form_1/fields/nr_de_servicos_por_agendamento/list/option">
                                                                                <option value="{value}" label="{text}">
                                                                                    <xsl:if test="@selected='true'">
                                                                                        <xsl:attribute name="selected">selected</xsl:attribute>
                                                                                    </xsl:if>
                                                                                    <span>
                                                                                        <xsl:value-of select="text"/>
                                                                                    </span>
                                                                                </option>
                                                                            </xsl:for-each>
                                                                        </select>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/hora_inicio">
                                                                    <div class="form-group col-md-3   gen-fields-holder" item-name="hora_inicio" item-type="text" required="required">
                                                                        <label for="{rows/content/form_1/fields/hora_inicio/@name}">
                                                                            <span>
                                                                                <xsl:value-of select="rows/content/form_1/fields/hora_inicio/label"/>
                                                                            </span>
                                                                        </label>
                                                                        <input type="text" value="{rows/content/form_1/fields/hora_inicio/value}" class="form-control " id="{rows/content/form_1/fields/hora_inicio/@name}" name="{rows/content/form_1/fields/hora_inicio/@name}" required="required" maxlength="6" placeholder=""></input>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/hora_fim">
                                                                    <div class="form-group col-md-3   gen-fields-holder" item-name="hora_fim" item-type="text" required="required">
                                                                        <label for="{rows/content/form_1/fields/hora_fim/@name}">
                                                                            <span>
                                                                                <xsl:value-of select="rows/content/form_1/fields/hora_fim/label"/>
                                                                            </span>
                                                                        </label>
                                                                        <input type="text" value="{rows/content/form_1/fields/hora_fim/value}" class="form-control " id="{rows/content/form_1/fields/hora_fim/@name}" name="{rows/content/form_1/fields/hora_fim/@name}" required="required" maxlength="6" placeholder=""></input>
                                                                    </div>
                                                                </xsl:if>
                                                                <xsl:if test="rows/content/form_1/fields/confirmacao_automatica">
                                                                    <div class="col-md-3 form-group  gen-fields-holder" item-name="confirmacao_automatica" item-type="select" required="required">
                                                                        <label for="{rows/content/form_1/fields/confirmacao_automatica/@name}">
                                                                            <xsl:value-of select="rows/content/form_1/fields/confirmacao_automatica/label"/>
                                                                        </label>
                                                                        <select class="form-control select2 " id="form_1_confirmacao_automatica" name="{rows/content/form_1/fields/confirmacao_automatica/@name}" required="required">
                                                                            <xsl:for-each select="rows/content/form_1/fields/confirmacao_automatica/list/option">
                                                                                <option value="{value}" label="{text}">
                                                                                    <xsl:if test="@selected='true'">
                                                                                        <xsl:attribute name="selected">selected</xsl:attribute>
                                                                                    </xsl:if>
                                                                                    <span>
                                                                                        <xsl:value-of select="text"/>
                                                                                    </span>
                                                                                </option>
                                                                            </xsl:for-each>
                                                                        </select>
                                                                    </div>
                                                                </xsl:if>
                                                            </div>
                                                        </div>
                                                        <xsl:apply-templates select="rows/content/form_1/tools-bar" mode="form-buttons"/>
                                                    </div>
                                                </xsl:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <xsl:call-template name="IGRP-bottom"/>
                </form>
                <!-- FORM JS INCLUDES -->
                <script type="text/javascript" src="{$path}/core/igrp/form/igrp.forms.js"/>
                <!-- SELECT JS INCLUDES -->
                <script type="text/javascript" src="{$path}/plugins/select2/select2.full.min.js"/>
                <script type="text/javascript" src="{$path}/plugins/select2/select2.init.js"/>
            </body>
        </html>
    </xsl:template>
    <xsl:include href="../../../xsl/tmpl/IGRP-functions.tmpl.xsl?v=1501680374763"/>
    <xsl:include href="../../../xsl/tmpl/IGRP-variables.tmpl.xsl?v=1501680374763"/>
    <xsl:include href="../../../xsl/tmpl/IGRP-home-include.tmpl.xsl?v=1501680374763"/>
    <xsl:include href="../../../xsl/tmpl/IGRP-utils.tmpl.xsl?v=1501680374763"/>
    <xsl:include href="../../../xsl/tmpl/IGRP-form-utils.tmpl.xsl?v=1501680374763"/>
</xsl:stylesheet>
