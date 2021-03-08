package com.organization.web.controller;

import com.organization.backend.domain.entity.Combination;
import com.organization.backend.service.CategoryPageService;
import com.organization.backend.service.CombinationService;
import com.organization.backend.service.exception.NotFoundException;
import com.organization.web.controller.api.ApiController;
import com.organization.web.dto.CombinationDto;
import com.organization.web.helper.BreadCrumbHelper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.organization.web.constants.UrlMappingConstants.SET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(SET)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SetController extends ApiController {

    @Autowired
    CombinationService combinationService;

    @Autowired
    CategoryPageService categoryPageService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value = "{combinationId}/upvote/", method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CombinationDto upVoteSet(@PathVariable("combinationId") long combinationId) {
        return modelMapper.map(combinationService.upVote(combinationId), CombinationDto.class);
    }

    @RequestMapping(value = "{combinationId}/downvote/", method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public CombinationDto downVoteSet(@PathVariable("combinationId") long combinationId) {
        return modelMapper.map(combinationService.downVote(combinationId), CombinationDto.class);
    }

}

