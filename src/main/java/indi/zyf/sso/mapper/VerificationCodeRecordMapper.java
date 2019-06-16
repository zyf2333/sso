package indi.zyf.sso.mapper;


import indi.zyf.sso.model.VerificationCodeRecord;

public interface VerificationCodeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VerificationCodeRecord record);

    int insertSelective(VerificationCodeRecord record);

    VerificationCodeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VerificationCodeRecord record);

    int updateByPrimaryKey(VerificationCodeRecord record);
}