package com.organization.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.organization.web.constants.UrlMappingConstants.Checkout.CHECKOUT_AMAZON;
import static com.organization.web.constants.UrlMappingConstants.Checkout.CHECKOUT_ROOT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(CHECKOUT_ROOT)
public class CheckoutController extends BaseController {

    @RequestMapping(value = CHECKOUT_AMAZON, method = GET)
    public String amazonCheckoutPage() {
        return Clickout.CHECKOUT_AMAZON;
    }
}
