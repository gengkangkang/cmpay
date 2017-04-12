package com.cmpay.service.chinapay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpPayOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CpPayOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPayOrderNoIsNull() {
            addCriterion("PAY_ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIsNotNull() {
            addCriterion("PAY_ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoEqualTo(String value) {
            addCriterion("PAY_ORDER_NO =", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotEqualTo(String value) {
            addCriterion("PAY_ORDER_NO <>", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThan(String value) {
            addCriterion("PAY_ORDER_NO >", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_ORDER_NO >=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThan(String value) {
            addCriterion("PAY_ORDER_NO <", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLessThanOrEqualTo(String value) {
            addCriterion("PAY_ORDER_NO <=", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoLike(String value) {
            addCriterion("PAY_ORDER_NO like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotLike(String value) {
            addCriterion("PAY_ORDER_NO not like", value, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoIn(List<String> values) {
            addCriterion("PAY_ORDER_NO in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotIn(List<String> values) {
            addCriterion("PAY_ORDER_NO not in", values, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoBetween(String value1, String value2) {
            addCriterion("PAY_ORDER_NO between", value1, value2, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andPayOrderNoNotBetween(String value1, String value2) {
            addCriterion("PAY_ORDER_NO not between", value1, value2, "payOrderNo");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("AMOUNT =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("AMOUNT >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("AMOUNT <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("AMOUNT in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("AMOUNT not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNull() {
            addCriterion("BANK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("BANK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("BANK_CODE =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("BANK_CODE <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("BANK_CODE >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_CODE >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("BANK_CODE <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("BANK_CODE <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("BANK_CODE like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("BANK_CODE not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("BANK_CODE in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("BANK_CODE not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("BANK_CODE between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("BANK_CODE not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNull() {
            addCriterion("CARD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCardNameIsNotNull() {
            addCriterion("CARD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCardNameEqualTo(String value) {
            addCriterion("CARD_NAME =", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotEqualTo(String value) {
            addCriterion("CARD_NAME <>", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThan(String value) {
            addCriterion("CARD_NAME >", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NAME >=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThan(String value) {
            addCriterion("CARD_NAME <", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLessThanOrEqualTo(String value) {
            addCriterion("CARD_NAME <=", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameLike(String value) {
            addCriterion("CARD_NAME like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotLike(String value) {
            addCriterion("CARD_NAME not like", value, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameIn(List<String> values) {
            addCriterion("CARD_NAME in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotIn(List<String> values) {
            addCriterion("CARD_NAME not in", values, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameBetween(String value1, String value2) {
            addCriterion("CARD_NAME between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNameNotBetween(String value1, String value2) {
            addCriterion("CARD_NAME not between", value1, value2, "cardName");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("CARD_NO is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("CARD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("CARD_NO =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("CARD_NO <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("CARD_NO >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NO >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("CARD_NO <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("CARD_NO <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("CARD_NO like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("CARD_NO not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("CARD_NO in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("CARD_NO not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("CARD_NO between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("CARD_NO not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("CARD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("CARD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("CARD_TYPE =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("CARD_TYPE <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("CARD_TYPE >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_TYPE >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("CARD_TYPE <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("CARD_TYPE <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("CARD_TYPE like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("CARD_TYPE not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("CARD_TYPE in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("CARD_TYPE not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("CARD_TYPE between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("CARD_TYPE not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCertIdIsNull() {
            addCriterion("CERT_ID is null");
            return (Criteria) this;
        }

        public Criteria andCertIdIsNotNull() {
            addCriterion("CERT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCertIdEqualTo(String value) {
            addCriterion("CERT_ID =", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdNotEqualTo(String value) {
            addCriterion("CERT_ID <>", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdGreaterThan(String value) {
            addCriterion("CERT_ID >", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_ID >=", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdLessThan(String value) {
            addCriterion("CERT_ID <", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdLessThanOrEqualTo(String value) {
            addCriterion("CERT_ID <=", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdLike(String value) {
            addCriterion("CERT_ID like", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdNotLike(String value) {
            addCriterion("CERT_ID not like", value, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdIn(List<String> values) {
            addCriterion("CERT_ID in", values, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdNotIn(List<String> values) {
            addCriterion("CERT_ID not in", values, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdBetween(String value1, String value2) {
            addCriterion("CERT_ID between", value1, value2, "certId");
            return (Criteria) this;
        }

        public Criteria andCertIdNotBetween(String value1, String value2) {
            addCriterion("CERT_ID not between", value1, value2, "certId");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNull() {
            addCriterion("CERT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNotNull() {
            addCriterion("CERT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCertTypeEqualTo(String value) {
            addCriterion("CERT_TYPE =", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotEqualTo(String value) {
            addCriterion("CERT_TYPE <>", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThan(String value) {
            addCriterion("CERT_TYPE >", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("CERT_TYPE >=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThan(String value) {
            addCriterion("CERT_TYPE <", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThanOrEqualTo(String value) {
            addCriterion("CERT_TYPE <=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLike(String value) {
            addCriterion("CERT_TYPE like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotLike(String value) {
            addCriterion("CERT_TYPE not like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeIn(List<String> values) {
            addCriterion("CERT_TYPE in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotIn(List<String> values) {
            addCriterion("CERT_TYPE not in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeBetween(String value1, String value2) {
            addCriterion("CERT_TYPE between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotBetween(String value1, String value2) {
            addCriterion("CERT_TYPE not between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("CITY =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("CITY <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("CITY >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("CITY <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("CITY like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("CITY not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("CITY in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("CITY not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCpDateIsNull() {
            addCriterion("CP_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCpDateIsNotNull() {
            addCriterion("CP_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCpDateEqualTo(String value) {
            addCriterion("CP_DATE =", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateNotEqualTo(String value) {
            addCriterion("CP_DATE <>", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateGreaterThan(String value) {
            addCriterion("CP_DATE >", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateGreaterThanOrEqualTo(String value) {
            addCriterion("CP_DATE >=", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateLessThan(String value) {
            addCriterion("CP_DATE <", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateLessThanOrEqualTo(String value) {
            addCriterion("CP_DATE <=", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateLike(String value) {
            addCriterion("CP_DATE like", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateNotLike(String value) {
            addCriterion("CP_DATE not like", value, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateIn(List<String> values) {
            addCriterion("CP_DATE in", values, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateNotIn(List<String> values) {
            addCriterion("CP_DATE not in", values, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateBetween(String value1, String value2) {
            addCriterion("CP_DATE between", value1, value2, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpDateNotBetween(String value1, String value2) {
            addCriterion("CP_DATE not between", value1, value2, "cpDate");
            return (Criteria) this;
        }

        public Criteria andCpSeqidIsNull() {
            addCriterion("CP_SEQID is null");
            return (Criteria) this;
        }

        public Criteria andCpSeqidIsNotNull() {
            addCriterion("CP_SEQID is not null");
            return (Criteria) this;
        }

        public Criteria andCpSeqidEqualTo(String value) {
            addCriterion("CP_SEQID =", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidNotEqualTo(String value) {
            addCriterion("CP_SEQID <>", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidGreaterThan(String value) {
            addCriterion("CP_SEQID >", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidGreaterThanOrEqualTo(String value) {
            addCriterion("CP_SEQID >=", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidLessThan(String value) {
            addCriterion("CP_SEQID <", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidLessThanOrEqualTo(String value) {
            addCriterion("CP_SEQID <=", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidLike(String value) {
            addCriterion("CP_SEQID like", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidNotLike(String value) {
            addCriterion("CP_SEQID not like", value, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidIn(List<String> values) {
            addCriterion("CP_SEQID in", values, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidNotIn(List<String> values) {
            addCriterion("CP_SEQID not in", values, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidBetween(String value1, String value2) {
            addCriterion("CP_SEQID between", value1, value2, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCpSeqidNotBetween(String value1, String value2) {
            addCriterion("CP_SEQID not between", value1, value2, "cpSeqid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCuryIdIsNull() {
            addCriterion("CURY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCuryIdIsNotNull() {
            addCriterion("CURY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCuryIdEqualTo(String value) {
            addCriterion("CURY_ID =", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdNotEqualTo(String value) {
            addCriterion("CURY_ID <>", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdGreaterThan(String value) {
            addCriterion("CURY_ID >", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdGreaterThanOrEqualTo(String value) {
            addCriterion("CURY_ID >=", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdLessThan(String value) {
            addCriterion("CURY_ID <", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdLessThanOrEqualTo(String value) {
            addCriterion("CURY_ID <=", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdLike(String value) {
            addCriterion("CURY_ID like", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdNotLike(String value) {
            addCriterion("CURY_ID not like", value, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdIn(List<String> values) {
            addCriterion("CURY_ID in", values, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdNotIn(List<String> values) {
            addCriterion("CURY_ID not in", values, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdBetween(String value1, String value2) {
            addCriterion("CURY_ID between", value1, value2, "curyId");
            return (Criteria) this;
        }

        public Criteria andCuryIdNotBetween(String value1, String value2) {
            addCriterion("CURY_ID not between", value1, value2, "curyId");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNull() {
            addCriterion("CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(String value) {
            addCriterion("CUST_ID =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(String value) {
            addCriterion("CUST_ID <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(String value) {
            addCriterion("CUST_ID >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUST_ID >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(String value) {
            addCriterion("CUST_ID <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(String value) {
            addCriterion("CUST_ID <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLike(String value) {
            addCriterion("CUST_ID like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotLike(String value) {
            addCriterion("CUST_ID not like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<String> values) {
            addCriterion("CUST_ID in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<String> values) {
            addCriterion("CUST_ID not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(String value1, String value2) {
            addCriterion("CUST_ID between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(String value1, String value2) {
            addCriterion("CUST_ID not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("FLAG =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("FLAG <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("FLAG >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("FLAG >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("FLAG <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("FLAG <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("FLAG like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("FLAG not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("FLAG in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("FLAG not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("FLAG between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("FLAG not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andJpaVersionIsNull() {
            addCriterion("JPA_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andJpaVersionIsNotNull() {
            addCriterion("JPA_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andJpaVersionEqualTo(Long value) {
            addCriterion("JPA_VERSION =", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionNotEqualTo(Long value) {
            addCriterion("JPA_VERSION <>", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionGreaterThan(Long value) {
            addCriterion("JPA_VERSION >", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionGreaterThanOrEqualTo(Long value) {
            addCriterion("JPA_VERSION >=", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionLessThan(Long value) {
            addCriterion("JPA_VERSION <", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionLessThanOrEqualTo(Long value) {
            addCriterion("JPA_VERSION <=", value, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionIn(List<Long> values) {
            addCriterion("JPA_VERSION in", values, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionNotIn(List<Long> values) {
            addCriterion("JPA_VERSION not in", values, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionBetween(Long value1, Long value2) {
            addCriterion("JPA_VERSION between", value1, value2, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andJpaVersionNotBetween(Long value1, Long value2) {
            addCriterion("JPA_VERSION not between", value1, value2, "jpaVersion");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNull() {
            addCriterion("MER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNotNull() {
            addCriterion("MER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerIdEqualTo(String value) {
            addCriterion("MER_ID =", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotEqualTo(String value) {
            addCriterion("MER_ID <>", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThan(String value) {
            addCriterion("MER_ID >", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThanOrEqualTo(String value) {
            addCriterion("MER_ID >=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThan(String value) {
            addCriterion("MER_ID <", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThanOrEqualTo(String value) {
            addCriterion("MER_ID <=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLike(String value) {
            addCriterion("MER_ID like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotLike(String value) {
            addCriterion("MER_ID not like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdIn(List<String> values) {
            addCriterion("MER_ID in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotIn(List<String> values) {
            addCriterion("MER_ID not in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdBetween(String value1, String value2) {
            addCriterion("MER_ID between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotBetween(String value1, String value2) {
            addCriterion("MER_ID not between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andProcDateIsNull() {
            addCriterion("PROC_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProcDateIsNotNull() {
            addCriterion("PROC_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProcDateEqualTo(Date value) {
            addCriterion("PROC_DATE =", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateNotEqualTo(Date value) {
            addCriterion("PROC_DATE <>", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateGreaterThan(Date value) {
            addCriterion("PROC_DATE >", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateGreaterThanOrEqualTo(Date value) {
            addCriterion("PROC_DATE >=", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateLessThan(Date value) {
            addCriterion("PROC_DATE <", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateLessThanOrEqualTo(Date value) {
            addCriterion("PROC_DATE <=", value, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateIn(List<Date> values) {
            addCriterion("PROC_DATE in", values, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateNotIn(List<Date> values) {
            addCriterion("PROC_DATE not in", values, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateBetween(Date value1, Date value2) {
            addCriterion("PROC_DATE between", value1, value2, "procDate");
            return (Criteria) this;
        }

        public Criteria andProcDateNotBetween(Date value1, Date value2) {
            addCriterion("PROC_DATE not between", value1, value2, "procDate");
            return (Criteria) this;
        }

        public Criteria andProvIsNull() {
            addCriterion("PROV is null");
            return (Criteria) this;
        }

        public Criteria andProvIsNotNull() {
            addCriterion("PROV is not null");
            return (Criteria) this;
        }

        public Criteria andProvEqualTo(String value) {
            addCriterion("PROV =", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvNotEqualTo(String value) {
            addCriterion("PROV <>", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvGreaterThan(String value) {
            addCriterion("PROV >", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvGreaterThanOrEqualTo(String value) {
            addCriterion("PROV >=", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvLessThan(String value) {
            addCriterion("PROV <", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvLessThanOrEqualTo(String value) {
            addCriterion("PROV <=", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvLike(String value) {
            addCriterion("PROV like", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvNotLike(String value) {
            addCriterion("PROV not like", value, "prov");
            return (Criteria) this;
        }

        public Criteria andProvIn(List<String> values) {
            addCriterion("PROV in", values, "prov");
            return (Criteria) this;
        }

        public Criteria andProvNotIn(List<String> values) {
            addCriterion("PROV not in", values, "prov");
            return (Criteria) this;
        }

        public Criteria andProvBetween(String value1, String value2) {
            addCriterion("PROV between", value1, value2, "prov");
            return (Criteria) this;
        }

        public Criteria andProvNotBetween(String value1, String value2) {
            addCriterion("PROV not between", value1, value2, "prov");
            return (Criteria) this;
        }

        public Criteria andResCodeIsNull() {
            addCriterion("RES_CODE is null");
            return (Criteria) this;
        }

        public Criteria andResCodeIsNotNull() {
            addCriterion("RES_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andResCodeEqualTo(String value) {
            addCriterion("RES_CODE =", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeNotEqualTo(String value) {
            addCriterion("RES_CODE <>", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeGreaterThan(String value) {
            addCriterion("RES_CODE >", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeGreaterThanOrEqualTo(String value) {
            addCriterion("RES_CODE >=", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeLessThan(String value) {
            addCriterion("RES_CODE <", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeLessThanOrEqualTo(String value) {
            addCriterion("RES_CODE <=", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeLike(String value) {
            addCriterion("RES_CODE like", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeNotLike(String value) {
            addCriterion("RES_CODE not like", value, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeIn(List<String> values) {
            addCriterion("RES_CODE in", values, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeNotIn(List<String> values) {
            addCriterion("RES_CODE not in", values, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeBetween(String value1, String value2) {
            addCriterion("RES_CODE between", value1, value2, "resCode");
            return (Criteria) this;
        }

        public Criteria andResCodeNotBetween(String value1, String value2) {
            addCriterion("RES_CODE not between", value1, value2, "resCode");
            return (Criteria) this;
        }

        public Criteria andResStatIsNull() {
            addCriterion("RES_STAT is null");
            return (Criteria) this;
        }

        public Criteria andResStatIsNotNull() {
            addCriterion("RES_STAT is not null");
            return (Criteria) this;
        }

        public Criteria andResStatEqualTo(String value) {
            addCriterion("RES_STAT =", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatNotEqualTo(String value) {
            addCriterion("RES_STAT <>", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatGreaterThan(String value) {
            addCriterion("RES_STAT >", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatGreaterThanOrEqualTo(String value) {
            addCriterion("RES_STAT >=", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatLessThan(String value) {
            addCriterion("RES_STAT <", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatLessThanOrEqualTo(String value) {
            addCriterion("RES_STAT <=", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatLike(String value) {
            addCriterion("RES_STAT like", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatNotLike(String value) {
            addCriterion("RES_STAT not like", value, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatIn(List<String> values) {
            addCriterion("RES_STAT in", values, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatNotIn(List<String> values) {
            addCriterion("RES_STAT not in", values, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatBetween(String value1, String value2) {
            addCriterion("RES_STAT between", value1, value2, "resStat");
            return (Criteria) this;
        }

        public Criteria andResStatNotBetween(String value1, String value2) {
            addCriterion("RES_STAT not between", value1, value2, "resStat");
            return (Criteria) this;
        }

        public Criteria andSubBankIsNull() {
            addCriterion("SUB_BANK is null");
            return (Criteria) this;
        }

        public Criteria andSubBankIsNotNull() {
            addCriterion("SUB_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andSubBankEqualTo(String value) {
            addCriterion("SUB_BANK =", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankNotEqualTo(String value) {
            addCriterion("SUB_BANK <>", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankGreaterThan(String value) {
            addCriterion("SUB_BANK >", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_BANK >=", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankLessThan(String value) {
            addCriterion("SUB_BANK <", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankLessThanOrEqualTo(String value) {
            addCriterion("SUB_BANK <=", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankLike(String value) {
            addCriterion("SUB_BANK like", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankNotLike(String value) {
            addCriterion("SUB_BANK not like", value, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankIn(List<String> values) {
            addCriterion("SUB_BANK in", values, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankNotIn(List<String> values) {
            addCriterion("SUB_BANK not in", values, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankBetween(String value1, String value2) {
            addCriterion("SUB_BANK between", value1, value2, "subBank");
            return (Criteria) this;
        }

        public Criteria andSubBankNotBetween(String value1, String value2) {
            addCriterion("SUB_BANK not between", value1, value2, "subBank");
            return (Criteria) this;
        }

        public Criteria andTransDateIsNull() {
            addCriterion("TRANS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTransDateIsNotNull() {
            addCriterion("TRANS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTransDateEqualTo(String value) {
            addCriterion("TRANS_DATE =", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotEqualTo(String value) {
            addCriterion("TRANS_DATE <>", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThan(String value) {
            addCriterion("TRANS_DATE >", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_DATE >=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThan(String value) {
            addCriterion("TRANS_DATE <", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLessThanOrEqualTo(String value) {
            addCriterion("TRANS_DATE <=", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateLike(String value) {
            addCriterion("TRANS_DATE like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotLike(String value) {
            addCriterion("TRANS_DATE not like", value, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateIn(List<String> values) {
            addCriterion("TRANS_DATE in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotIn(List<String> values) {
            addCriterion("TRANS_DATE not in", values, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateBetween(String value1, String value2) {
            addCriterion("TRANS_DATE between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransDateNotBetween(String value1, String value2) {
            addCriterion("TRANS_DATE not between", value1, value2, "transDate");
            return (Criteria) this;
        }

        public Criteria andTransIdIsNull() {
            addCriterion("TRANS_ID is null");
            return (Criteria) this;
        }

        public Criteria andTransIdIsNotNull() {
            addCriterion("TRANS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTransIdEqualTo(String value) {
            addCriterion("TRANS_ID =", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotEqualTo(String value) {
            addCriterion("TRANS_ID <>", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdGreaterThan(String value) {
            addCriterion("TRANS_ID >", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_ID >=", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLessThan(String value) {
            addCriterion("TRANS_ID <", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLessThanOrEqualTo(String value) {
            addCriterion("TRANS_ID <=", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdLike(String value) {
            addCriterion("TRANS_ID like", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotLike(String value) {
            addCriterion("TRANS_ID not like", value, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdIn(List<String> values) {
            addCriterion("TRANS_ID in", values, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotIn(List<String> values) {
            addCriterion("TRANS_ID not in", values, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdBetween(String value1, String value2) {
            addCriterion("TRANS_ID between", value1, value2, "transId");
            return (Criteria) this;
        }

        public Criteria andTransIdNotBetween(String value1, String value2) {
            addCriterion("TRANS_ID not between", value1, value2, "transId");
            return (Criteria) this;
        }

        public Criteria andTransStatusIsNull() {
            addCriterion("TRANS_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andTransStatusIsNotNull() {
            addCriterion("TRANS_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andTransStatusEqualTo(String value) {
            addCriterion("TRANS_STATUS =", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotEqualTo(String value) {
            addCriterion("TRANS_STATUS <>", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusGreaterThan(String value) {
            addCriterion("TRANS_STATUS >", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_STATUS >=", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLessThan(String value) {
            addCriterion("TRANS_STATUS <", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLessThanOrEqualTo(String value) {
            addCriterion("TRANS_STATUS <=", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLike(String value) {
            addCriterion("TRANS_STATUS like", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotLike(String value) {
            addCriterion("TRANS_STATUS not like", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusIn(List<String> values) {
            addCriterion("TRANS_STATUS in", values, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotIn(List<String> values) {
            addCriterion("TRANS_STATUS not in", values, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusBetween(String value1, String value2) {
            addCriterion("TRANS_STATUS between", value1, value2, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotBetween(String value1, String value2) {
            addCriterion("TRANS_STATUS not between", value1, value2, "transStatus");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoIsNull() {
            addCriterion("ORIG_ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoIsNotNull() {
            addCriterion("ORIG_ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoEqualTo(String value) {
            addCriterion("ORIG_ORDER_NO =", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoNotEqualTo(String value) {
            addCriterion("ORIG_ORDER_NO <>", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoGreaterThan(String value) {
            addCriterion("ORIG_ORDER_NO >", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORIG_ORDER_NO >=", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoLessThan(String value) {
            addCriterion("ORIG_ORDER_NO <", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ORIG_ORDER_NO <=", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoLike(String value) {
            addCriterion("ORIG_ORDER_NO like", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoNotLike(String value) {
            addCriterion("ORIG_ORDER_NO not like", value, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoIn(List<String> values) {
            addCriterion("ORIG_ORDER_NO in", values, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoNotIn(List<String> values) {
            addCriterion("ORIG_ORDER_NO not in", values, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoBetween(String value1, String value2) {
            addCriterion("ORIG_ORDER_NO between", value1, value2, "origOrderNo");
            return (Criteria) this;
        }

        public Criteria andOrigOrderNoNotBetween(String value1, String value2) {
            addCriterion("ORIG_ORDER_NO not between", value1, value2, "origOrderNo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}