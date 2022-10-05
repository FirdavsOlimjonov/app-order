package uz.pdp.appproduct.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appproduct.dto.MenuItemDTO;

import java.util.List;

@RequestMapping("api/menu")
@RestController
public interface MenuController {


    @GetMapping
    List<MenuItemDTO> get();

}
