import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Header from '../../components/Header';
import Footer from '../../components/Footer';
import SideBar from '../../components/SideBar';
import Button from '../../components/Button/Button';

const Section = styled.section`
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: yellowgreen;
`;
const Main = styled.main`
  background-color: beige;
  width: 90vw;
  padding: 0 5vw;
  max-width: 500px;
  & form {
    display: flex;
    flex-direction: column;
  }
  @media screen and (max-width: 64rem) {
    width: 90vw;
  }
`;

const NotUserOrderPage = ({ onClick, isOpen }) => {
  return (
    <Section>
      <Header />
      <Main>
        <h1>주문 상품 정보</h1>
        <h1>주문자 정보</h1>
        <form>
          <label htmlFor="name">이름</label>
          <input name="name" type="text" />
          <label htmlFor="address">주소</label>
          <input name="address" type="text" />
          <label htmlFor="phoneNum">전화번호</label>
          <input name="phoneNum" type="text" />
          <label htmlFor="email">이메일</label>
          <input name="email" type="email" />
        </form>
        <h1>결제 정보</h1>
        <p>총 수량: 개</p>
        <p>총 가격: 원</p>
        <Button text="결제하기" />
        <Button text="취소" />
      </Main>
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
};

NotUserOrderPage.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
};

export default NotUserOrderPage;
