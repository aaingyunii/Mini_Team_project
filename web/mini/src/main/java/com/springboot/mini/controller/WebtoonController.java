package com.springboot.mini.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebtoonController {
    private final WebtoonService webtoonService;

    @Autowired
    public WebtoonController(WebtoonService webtoonService){
        this.webtoonService = webtoonService;
    }

    
    @RequestMapping("/webtoons")
    public RedirectView redirectToFirstPage() {
        return new RedirectView("/webtoons?page=0");
    }

    // 전체 조회(메인) 페이지 연결
    @GetMapping("/webtoons")
    public String index(Model model, @RequestParam(defaultValue = "1") int page){
        int pageSize=12;
        Pageable pageable = PageRequest.of(page-1, pageSize);

        // log.info("나 여기 있어!!");

        // 1. 모든 데이터 가져오기
        Page<WebtoonDto> webtooonPage = webtoonService.getWebtoonAll(page-1, pageSize);
        log.info("PageNum : "+webtooonPage.getPageable());
        
        int first = 1;
        int totalPage = webtooonPage.getTotalPages();
        // log.info("토탈토탈 : "+totalPage);
        int nowPage = pageable.getPageNumber();
        log.info("나우나우 : "+nowPage);

        int beginPage = (nowPage / pageSize) * pageSize + 1;
        log.info("비긴 : "+beginPage);
        int endPage = Math.min(beginPage + pageSize -1, totalPage);

        int previous = Math.max(beginPage - pageSize, 1);

        log.info("previous : "+previous);

        int next = Math.min(endPage + 1, totalPage);

        log.info("next : "+next);

        ArrayList<Integer> pageIndex = new ArrayList<Integer>();
        
        for (int i = beginPage; i <= endPage; i++){
            pageIndex.add(i);
        }

        log.info("페이지 인덱스 :"+ pageIndex);
        // 2. 모델에 데이터 등록
        model.addAttribute("webtoonList", webtooonPage);
        model.addAttribute("first", first);
        model.addAttribute("last",totalPage);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("nowPage", nowPage);
        
        // 3. 뷰에 전송
        return "webtoons/main";
    }

    // 한 웹툰 선택 시 상세 내용 조회 페이지 연결
    @GetMapping("/webtoons/{webtoonId}")
    public String detail(@PathVariable Integer webtoonId, Model model){
        log.info("id 값은? : "+ webtoonId);
        
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonByWebtoonId(webtoonId);
        log.info("Dto : "+webtoonDtos);

        model.addAttribute("webtoons", webtoonDtos);

        return "webtoons/detail";
    }

    // 검색 기능 페이지 연결
    @GetMapping("/webtoons/search/{every}")
    public String show(@PathVariable String every, Model model) {
        List<WebtoonDto> webtoonDtos = webtoonService.searchWebtoonsByEvery(every);

        model.addAttribute("webtoonList", webtoonDtos);
        
        return "webtoons/show";
    }

    @GetMapping("/webtoons/{genre}/rank")
    public String rankShow(@PathVariable String genre, Model model){
        String genreCode = webtoonService.getGenreCode(genre);
        String genreKorean = webtoonService.getGenreKorean(genre);
        List<WebtoonGenreRankDto> genreRankDtos = webtoonService.getJoinDtos(genreCode);
        
        System.out.println(genreKorean);
        log.info("장르 랭크 DTO : "+genreRankDtos);
        
        model.addAttribute("webtoonRankList", genreRankDtos);
        model.addAttribute("genreKorean", genreKorean);
        
        return "webtoons/rank";
    }

}
