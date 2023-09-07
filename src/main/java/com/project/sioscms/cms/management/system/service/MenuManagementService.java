package com.project.sioscms.cms.management.system.service;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.apps.menu.domain.repository.MenuRepository;
import com.project.sioscms.apps.menu.mapper.MenuMapper;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuManagementService extends EgovAbstractServiceImpl {
    private final MenuRepository menuRepository;

    public SiosPage<MenuDto.Response> getMenuList(CommonSearchDto requestDto){
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();


        return new SiosPage<>(menuRepository.findAll(restriction.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Menu::toResponse));
    }

    @Transactional
    public boolean saveMenu(MenuDto.Request request){
        try {
            Menu entity = MenuMapper.mapper.toEntity(request);
            if (request.getUpperMenuId() != null) {
                Menu upperMenu = menuRepository.findById(request.getUpperMenuId()).orElseThrow(NullPointerException::new);
                entity.setUpperMenu(upperMenu);
            }
            menuRepository.save(entity);
            return true;
        }catch (NullPointerException e){
            log.error(e.toString());
            return false;
        }
    }
}
