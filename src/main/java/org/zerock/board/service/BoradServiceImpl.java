package org.zerock.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Borad;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2

public class BoradServiceImpl implements BoardService {

    private final BoardRepository repository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto){
        log.info(dto);
        Borad borad = dtoToEntity(dto);
        repository.save(borad);
        return borad.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Borad) en[0], (Member) en[1], (Long) en[2]));

        //Page<Object[]> result = repository.getBoardWithReplyConunt(pageRequestDTO.getPageable(Sort.by(("bno")).descending()));

        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoradByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Borad) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        repository.deleteById(bno);
    }

    @Override
    @Transactional
    public void modify(BoardDTO boardDTO) {
        Borad borad = repository.getReferenceById(boardDTO.getBno());

        if(borad != null) {
            borad.changeTitle(boardDTO.getTitle());
            borad.changeContent(boardDTO.getContent());
            repository.save(borad);
        }
    }

//    @Override
//    public BoardDTO read(Long bno) {
//        Optional<Borad> result = repository.findById(bno);
//        return result.isPresent() ? entityToDTO(result.get(), ) : null;
//    }

//    @Override
//    public void remove(Long gno) {
//        repository.deleteById(gno);
//    }

}
