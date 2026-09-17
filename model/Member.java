package com.library.model;

public class Member {
    private int memberId;
    private String name;
    private String email;
    private String phone;
    private String joinedOn; // stored as yyyy-MM-dd string

    public Member(int memberId, String name, String email, String phone, String joinedOn) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.joinedOn = joinedOn;
    }

    public int getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getJoinedOn() { return joinedOn; }

    public String toFileLine() {
        return memberId + "|" + name + "|" + email + "|" + phone + "|" + joinedOn;
    }

    public static Member fromFileLine(String line) {
        String[] p = line.split("\\|", -1);
        return new Member(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]);
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d | %-25s | %-25s | %-12s | Joined:%s",
                memberId, name, email, phone, joinedOn);
    }
}
