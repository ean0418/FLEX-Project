package com.flex.bizone.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

@Mapper
public interface MemberMapper {
    public abstract int signupMember(Bizone_member m);
    public abstract List<Bizone_member> getMemberById(Bizone_member m);
    public abstract int deleteMember(Bizone_member m);
    public abstract int updateMember(Bizone_member m);

    public abstract List<Bizone_member> getIdByEmail(Bizone_member m);
    public abstract int changePW(Bizone_member m);
    int checkIfIdExists(Bizone_member m);
}
