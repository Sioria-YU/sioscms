package com.project.sioscms.apps.menu.service;

import com.project.sioscms.cms.management.system.domain.dto.MenuRequestDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto.Response;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.apps.menu.domain.repository.MenuRepository;
import com.project.sioscms.apps.menu.mapper.MenuMapper;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    /**
     * 메뉴 목록 조회
     * @param request MenuDto.Request
     * @return List<Response>
     */
    public List<Response> getMenuList(MenuDto.Request request){
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);
        restriction.equals("isDeleted", false);

        //root여부
        if (request.getIsRoot() != null && request.getIsRoot()) {
            restriction.equals("isRoot", true);
        } else if (request.getIsRoot() != null && !request.getIsRoot()) {
            restriction.equals("isRoot", false);
        }

        return menuRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.ASC, "orderNum"))
                .stream().map(Menu::toResponse).collect(Collectors.toList());
    }

    /**
     * 메뉴 생성
     * @param request MenuDto.Request
     * @return boolean
     */
    @Transactional
    public boolean saveMenu(MenuDto.Request request) {
        try {
            Menu entity = MenuMapper.mapper.toEntity(request);
            if (request.getUpperMenuId() != null) {
                Menu upperMenu = menuRepository.findById(request.getUpperMenuId()).orElseThrow(NullPointerException::new);
                entity.setUpperMenu(upperMenu);
                Menu maxOrderMenu = menuRepository.findTop1ByIsDeletedOrderByOrderNumDesc(false).orElseThrow(NullPointerException::new);
                entity.setOrderNum(maxOrderMenu.getOrderNum() + 1);
            }
            menuRepository.save(entity);
            return true;
        } catch (NullPointerException e) {
            log.error(e.toString());
            return false;
        }
    }

    /**
     * 메뉴 수정
     * @param request MenuRequestDto
     * @return boolean
     */
    @Transactional
    public boolean updateMenu(MenuRequestDto request) {
        try {
            Menu menu = menuRepository.findById(request.getMenuId()).orElseThrow(NullPointerException::new);

            if (!Objects.equals(request.getUpperMenuId(), menu.getUpperMenu().getId())) {
                Menu upperMenu = menuRepository.findById(request.getUpperMenuId()).orElseThrow(NullPointerException::new);
                menu.setUpperMenu(upperMenu);
            }
            menu.setMenuName(request.getMenuName());
            menu.setMenuType(request.getMenuType());
            menu.setMenuLink(request.getMenuLink());
            menu.setIsUsed(request.getIsUsed());

            return true;
        } catch (NullPointerException e) {
            log.error(e.toString());
            return false;
        }
    }

    /**
     * 메뉴 이동 이벤트
     * @param id 메뉴 아이디
     * @param upperMenuId 상위 메뉴 아이디
     * @param orderNum 정렬순서
     * @return
     */
    @Transactional
    public boolean updateMenuOrder(long id, long upperMenuId, long orderNum) {
        try {
            Menu menu = menuRepository.findById(id).orElseThrow(NullPointerException::new);

            if (orderNum != menu.getOrderNum()) {
                if (orderNum > menu.getOrderNum()) {
                    menuRepository.updateByOrders(menu.getOrderNum(), menu.getOrderNum(), orderNum, false, -1L);
                } else {
                    menuRepository.updateByOrders(menu.getOrderNum(), orderNum, menu.getOrderNum(), false, 1L);
                }
            }
            menu.setOrderNum(orderNum);
            if (!Objects.equals(menu.getUpperMenu().getId(), upperMenuId)) {
                Menu upperMenu = menuRepository.findById(upperMenuId).orElseThrow(NullPointerException::new);
                menu.setUpperMenu(upperMenu);
            }

            return true;
        } catch (NullPointerException e) {
            log.error(e.toString());
            return false;
        }
    }

    /**
     * 메뉴 업/다운 버튼 이동 이벤트
     * @param request :MenuRequestDto
     * @return boolean
     */
    @Transactional
    public boolean updateUpdownOrder(MenuRequestDto request) {
        try {
            if (request.getMenuOrderType() != null) {
                Menu menu = menuRepository.findById(request.getMenuId()).orElseThrow(NullPointerException::new);

                long order = menu.getOrderNum();

                switch (request.getMenuOrderType().name()){
                    case "UP" -> order--;
                    case "DOWN" -> order++;
                }
                if(order < 0) return false;

                Menu moveToOrder = menuRepository.findTop1ByIsDeletedAndOrderNumOrderById(false, order).orElseThrow(NullPointerException::new);
                //현재 부모 위로 올라갈 경우
                if(!menu.getUpperMenu().getId().equals(moveToOrder.getUpperMenu().getId()) && menu.getUpperMenu().getId().equals(moveToOrder.getId())) {
                    menu.setUpperMenu(moveToOrder.getUpperMenu());
                    moveToOrder.setOrderNum(menu.getOrderNum());
                    menu.setOrderNum(order);
                }//부모 메뉴가 다를 경우
                else if(!menu.getUpperMenu().getId().equals(moveToOrder.getUpperMenu().getId())){
                    menu.setUpperMenu(moveToOrder.getUpperMenu());
                }//부모가 같은 경우
                else{
                    //이동하려는 순번 하위에 자식이 있는 경우 해당 메뉴 하위로 이동
                    if(menuRepository.countByUpperMenu_IdAndIsDeleted(moveToOrder.getId(), false) > 0){
                        menu.setUpperMenu(moveToOrder);
                        moveToOrder.setOrderNum(menu.getOrderNum());
                        menu.setOrderNum(order);
                    }//순서만 변경
                    else {
                        moveToOrder.setOrderNum(menu.getOrderNum());
                        menu.setOrderNum(order);
                    }
                }
//                menuRepository.flush();
                return true;
            }
            return false;
        } catch (NullPointerException e) {
            log.error(e.toString());
            return false;
        }
    }

    @Transactional
    public boolean deleteMenu(Long id){
        //1. 메뉴 조회
        Menu menu = menuRepository.findById(id).orElse(null);

        if(menu == null){
            return false;
        }

        //2. 메뉴 순서 취득
        long orderNum = menu.getOrderNum();

        //3. 메뉴 삭제
        menu.setIsUsed(false);
        menu.setIsDeleted(true);

        //4. 메뉴 순서 업데이트
        menuRepository.updateByOrders(orderNum, orderNum, Long.MAX_VALUE, false, -1L);
        menuRepository.flush();

        return true;
    }
}
