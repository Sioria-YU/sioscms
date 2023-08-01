package com.project.sioscms.apps.hashtag.service;

import com.project.sioscms.apps.hashtag.domain.dto.HashtagDto;
import com.project.sioscms.apps.hashtag.domain.entity.Hashtag;
import com.project.sioscms.apps.hashtag.domain.repository.HashtagRepository;
import com.project.sioscms.apps.hashtag.mapper.HashtagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public HashtagDto.Response saveHashtag(HashtagDto.Request requestDto){
        Hashtag hashtag = HashtagMapper.mapper.toEntity(requestDto);
        hashtag.setIsDeleted(false);
        hashtag.setIsDisplay(true);

        hashtagRepository.save(hashtag);

        return hashtag.toResponse();
    }
}
