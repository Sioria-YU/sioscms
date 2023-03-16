package com.project.sioscms.apps.menu.controller;

import com.project.sioscms.apps.cms.management.system.domain.dto.MenuRequestDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto.Response;
import com.project.sioscms.apps.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cms/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/list")
    public ResponseEntity<List<Response>> getMenuList(MenuDto.Request request) throws Exception{
        return ResponseEntity.ok(menuService.getMenuList(request));
    }

    @PutMapping("/save")
    public ResponseEntity<Boolean> saveMenu(MenuDto.Request request) throws Exception{
        return ResponseEntity.ok(menuService.saveMenu(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateMenu(MenuRequestDto request) throws Exception{
        return ResponseEntity.ok(menuService.updateMenu(request));
    }

    @PutMapping("/update-order")
    public ResponseEntity<Boolean> updateMenuOrder(MenuRequestDto request) throws Exception{
        return ResponseEntity.ok(menuService.updateMenuOrder(request.getId(), request.getUpperMenuId(), request.getOrderNum()));
    }

    @PutMapping("/update-updown-order")
    public ResponseEntity<Boolean> updateUpdownOrder(MenuRequestDto request) throws Exception{
        return ResponseEntity.ok(menuService.updateUpdownOrder(request));
    }
}
