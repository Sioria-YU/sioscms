package com.project.sioscms.apps.menu.controller;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto.Response;
import com.project.sioscms.apps.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    public ResponseEntity<List<Response>> getMenuList(MenuDto.Request request) throws Exception{
        return ResponseEntity.ok(menuService.getMenuList(request));
    }
}
