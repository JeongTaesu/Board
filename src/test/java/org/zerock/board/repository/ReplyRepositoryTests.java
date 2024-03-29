package org.zerock.board.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Borad;
import org.zerock.board.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1,300).forEach(i ->{
            long bno = (long) (Math.random() * 100) +1;
            Borad borad = Borad.builder()
                    .bno(bno)
                    .build();

            Reply reply = Reply.builder()
                    .text("Reply...." + i)
                    .borad(borad)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);

        });
    }

    @Test
    public void readReply1() {
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBorad());
    }


    @Test
    public void testListByBorad() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Borad.builder().bno(97L).build());
        if(replyList != null) {
            replyList.forEach(reply -> {
                System.out.println(reply);
            });
        }

    }


}
