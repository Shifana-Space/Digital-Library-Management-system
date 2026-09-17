package com.library.repository;

import com.library.model.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private static final String FILE_PATH = "data/members.txt";

    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        for (String line : FileStorage.readLines(FILE_PATH)) {
            members.add(Member.fromFileLine(line));
        }
        return members;
    }

    public void addMember(Member member) {
        FileStorage.appendLine(FILE_PATH, member.toFileLine());
    }

    public Member getMemberById(int memberId) {
        for (Member m : getAllMembers()) {
            if (m.getMemberId() == memberId) return m;
        }
        return null;
    }

    public int getNextId() {
        int max = 0;
        for (Member m : getAllMembers()) max = Math.max(max, m.getMemberId());
        return max + 1;
    }
}
