@echo off
call mvn clean install --U -X -DskipTests=true
call copy C:\\WebDSP_BR\\indico-ear\\target\\indico-ear.ear C:\\DSP-Docs\\jboss-server\\jboss-eap-7.3\\standalone\\deployments
