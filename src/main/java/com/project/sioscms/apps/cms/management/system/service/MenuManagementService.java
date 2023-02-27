package com.project.sioscms.apps.cms.management.system.service;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.apps.menu.domain.repository.MenuRepository;
import com.project.sioscms.common.domain.dto.CommonSearchDto;
import com.project.sioscms.common.utils.jpa.page.SiosPage;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuManagementService {
    private final MenuRepository menuRepository;

    public SiosPage<MenuDto.Response> getMenuList(CommonSearchDto requestDto){
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction();


        return new SiosPage<>(menuRepository.findAll(restriction.toSpecification()
                , requestDto.toPageableWithSortedByCreatedDateTime(Sort.Direction.DESC)).map(Menu::toResponse));
    }

}
