import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

import Login from './Login';
import NotUserLogin from './NotUserLogin';

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
  min-height: 300px;
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
  height: 50%;
  min-height: 300px;
`;

const LoginPage = () => {
  const [isForUser, setIsForUser] = useState(true);
  const handleShow = (e) => {
    if (e.target.textContent === '회원') {
      setIsForUser(true);
    } else {
      setIsForUser(false);
    }
  };

  return (
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
        <Main>{isForUser ? <Login /> : <NotUserLogin />}</Main>
      </Section>
    </Div>
  );
};

export default LoginPage;
