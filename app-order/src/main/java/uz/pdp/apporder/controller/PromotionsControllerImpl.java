package uz.pdp.apporder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionsControllerImpl implements PromotionsController {

    private final PromotionsService promotionsService;

}
