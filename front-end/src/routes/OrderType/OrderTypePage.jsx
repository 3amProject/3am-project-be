import React, { useState } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

import Login from '../Login/Login';
import NotUserOrder from '../NotUserOrder/NotUserOrder';

const Div = styled.div`
  width: 100vw;
  height: 100vh;
  background-color: var(--beige);
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Section = styled.section`
  position: relative;
  width: 60%;
  max-width: 500px;
  height: 80%;
  overflow: scroll;
  background-color: white;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  @media screen and (max-width: 64rem) {
    width: 85%;
    height: 90%;
  }
`;
const Header = styled.header`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  & .title {
    margin-bottom: 20px;
  }
  & .buttons {
    width: 100%;
    height: 32px;
    display: flex;
    & button {
      flex: 1;
      border-bottom: 2px solid var(--lightgray);
      &: hover {
        border-bottom: 2px solid black;
      }
    }
  }
`;
const Main = styled.main`
  width: 80%;
  height: 60%;
`;

const OrderTypePage = ({ authUser }) => {
  const [isUser, setIsUser] = useState(true);

  // security api 로 로그인 화면 조회해야 함
  const handleShow = (e) => {
    if (e.target.textContent === '회원') {
      setIsUser(true);
    } else {
      setIsUser(false);
    }
  };

  return authUser ? (
    (window.location.href = '/userOrder')
  ) : (
    <Div>
      <Section>
        <Header>
          <Link to="/">
            <button type="button" className="closeBtn">
              <i className="fas fa-times" />
            </button>
          </Link>
          <h1 className="title">로그인</h1>
          <div className="buttons">
            <button type="button" onClick={handleShow}>
              회원
            </button>
            <button type="button" onClick={handleShow}>
              비회원
            </button>
          </div>
        </Header>
        <Main>{isUser ? <Login /> : <NotUserOrder />}</Main>
      </Section>
    </Div>
  );
};

OrderTypePage.propTypes = {
  authUser: PropTypes.bool.isRequired,
};

export default OrderTypePage;
