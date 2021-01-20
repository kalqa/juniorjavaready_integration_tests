package com.javaready.verifier.products.verification;

import com.javaready.product.Product;
import com.javaready.verifier.Verification;

class BarCodeVerification implements Verification {

    public static final int BAR_CODE_LOWER_BOUND_LENGTH = 1;
    public static final int BAR_CODE_UPPER_BOUND_LENGTH = 12;

    @Override
    public boolean passes(Product product) {
        final int barCodeLength = product.getBarCode().length();
        return isBarCodeLengthCorrect(barCodeLength);
    }

    private boolean isBarCodeLengthCorrect(int barCodeLength) {
        return barCodeLength >= BAR_CODE_LOWER_BOUND_LENGTH && barCodeLength <= BAR_CODE_UPPER_BOUND_LENGTH;
    }
}
