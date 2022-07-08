package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Borad;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    default Borad dtoToEntity(BoardDTO dto) {
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();
        Borad borad = Borad.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return borad;
    }

    default BoardDTO entityToDTO(Borad borad, Member member, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(borad.getBno())
                .title(borad.getTitle())
                .content(borad.getContent())
                .regDate(borad.getRegDate())
                .modDate(borad.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;
    }

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);


    //BoardDTO read(Long bno);

}
