package br.com.lucio.domain.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class Document {

    private String documentNumber;
    private static final int[] CALC_CHAR_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] CALC_CHAR_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public static Document of(String documentNumber) {
        return new Document(documentNumber);
    }

    private Document(String documentNumber) {
        if (!isValido(documentNumber)) {
            throw new ServiceException("Field document is invalid");
        }
        this.documentNumber = documentNumber;
    }

    public static boolean isValido(String document) {
        if (StringUtils.isBlank(document)) {
            throw new ServiceException("Field 'document' must not be blank");
        }

        if (!Pattern.matches("\\d+", document)) {
            return false;
        }

        if ((document.length() != 11 && document.length() != 14)) {
            return false;
        }

        if (document.length() == 11) {
            return isValidCPF(document);
        }
        return isValidCNPJ(document);
    }

    private static int calculateDigit(String str, int[] calcChar) {
        int sum = 0;
        for (int index = str.length() - 1, digit; index >= 0; index--) {
            digit = Integer.parseInt(str.substring(index, index + 1));
            sum += digit * calcChar[calcChar.length - str.length() + index];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    public static boolean isValidCPF(String cpf) {
        Integer digitOne = calculateDigit(cpf.substring(0, 9), CALC_CHAR_CPF);
        Integer digitTwo = calculateDigit(cpf.substring(0, 9) + digitOne, CALC_CHAR_CPF);
        return cpf.equals(cpf.substring(0, 9) + digitOne + digitTwo);
    }

    public static boolean isValidCNPJ(String cnpj) {
        Integer digitOne = calculateDigit(cnpj.substring(0, 12), CALC_CHAR_CNPJ);
        Integer digit2 = calculateDigit(cnpj.substring(0, 12) + digitOne, CALC_CHAR_CNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digitOne + digit2);
    }

    @Override
    public String toString() {
        return documentNumber;
    }

}
