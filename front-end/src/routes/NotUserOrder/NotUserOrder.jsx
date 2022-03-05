import React from 'react';
import styled from 'styled-components';

import Button from '../../components/Button/Button';

const Div = styled.div`
  & .title {
    margin: 30px 0 10px 0;
  }
  & .subTitle {
    font-size: 12px;
  }
  & .buttons {
    display: flex;
    flex-direction: column;
  }
  & .input {
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  & .text {
    font-size: 14px;
    text-align: center;
  }
`;
const Text = styled.div`
  height: 100px;
  padding: 10px;
  margin: 20px 10px;
  font-size: 14px;
  line-height: 20px;
  overflow: scroll;
  border: 1px solid black;
`;

const NotUserOrder = () => {
  return (
    <Div>
      <h3 className="title">비회원 구매</h3>
      <span className="subTitle">
        비회원으로 주문하시는 경우 포인트는 지급하지 않습니다.
      </span>
      <Text>
        <h4>비회원정보수집 동의</h4>
        <p>
          비회원 개인정보보호정책은 비회원으로 주문하는 고객님의 개인정보 보호를
          위하여 새벽다섯시가 실시하는 개인정보 수집의 목적과 그 정보의 정책에
          관한 규정입니다.
        </p>
        <h4>제1조 개인정보 수집 범위</h4>
        <p>필수항목: 성명, 휴대전화번호, 이메일, 구매상품, 상품 사이즈</p>
      </Text>
      <div className="input">
        <label htmlFor="agree">
          개인정보수집에 대한 내용을 읽었으며 이에 동의합니다.
        </label>
        <input name="agree" type="checkbox" />
      </div>
      <div className="buttons">
        <Button
          text="비회원으로 주문"
          onClick={() => {
            window.location.href = '/notUserOrder';
          }}
        />
        <p className="text">회원이 되면 더 많은 혜택이!</p>
        <Button
          text="회원가입"
          onClick={() => {
            window.location.href = '/join';
          }}
        />
      </div>
    </Div>
  );
};

export default NotUserOrder;
