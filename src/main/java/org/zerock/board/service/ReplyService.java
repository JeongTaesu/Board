package org.zerock.board.service;


import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Borad;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO); //댓글의 등록

    List<ReplyDTO> getList(Long bno); //특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO); //댓글 수정

    void remove(Long rno); //댓글 삭제

    default Reply dtoToEntity(ReplyDTO replyDTO){

        Borad borad = Borad.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .borad(borad)
                .build();

        return reply;
    }

    default ReplyDTO entityToDTO(Reply reply){

        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;

    }

}
