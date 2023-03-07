package com.project.sioscms.apps.menu.service;

import com.project.sioscms.apps.menu.domain.dto.MenuDto;
import com.project.sioscms.apps.menu.domain.dto.MenuDto.Response;
import com.project.sioscms.apps.menu.domain.entity.Menu;
import com.project.sioscms.apps.menu.domain.repository.MenuRepository;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestriction;
import com.project.sioscms.common.utils.jpa.restriction.ChangSolJpaRestrictionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Response> getMenuList(MenuDto.Request request) throws Exception{
        ChangSolJpaRestriction restriction = new ChangSolJpaRestriction(ChangSolJpaRestrictionType.AND);

        //root여부
        if(request.getIsRoot() != null && request.getIsRoot()){
            restriction.equals("isRoot", true);
        }else if(request.getIsRoot() != null && !request.getIsRoot()){
            restriction.equals("isRoot", false);
        }

        return menuRepository.findAll(restriction.toSpecification(), Sort.by(Sort.Direction.ASC, "upperMenuId", "id"))
                .stream().map(Menu::toResponse).collect(Collectors.toList());

    }
}
