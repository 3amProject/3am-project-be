import React, { useState } from 'react';
import styled from 'styled-components';
import { signIn } from '../../service/authService';

import Button from '../../components/Button/Button';

const Div = styled.div`
  margin: 30px 0;
  display: flex;
  flex-direction: column;
  & .input {
    padding: 5px 0 5px 10px;
    margin-bottom: 10px;
    font-size: 16px;
    border: 1.5px solid var(--gray);
    border-radius: 10px;
  }
  & .checkForms {
    display: flex;
    justify-content: space-between;
    & .checkForm {
      font-size: 14px;
    }
  }
`;
const Footer = styled.footer`
  display: flex;
  flex-direction: column;
  & .buttons {
    display: flex;
    justify-content: center;
    & span {
      border-right: 1px solid black;
    }
  }
`;

const Login = () => {
  const [formData, setFormData] = useState({
    userId: '',
    password: '',
  });
  const handleLogin = async (user) => {
    const data = await signIn(user);
    if (data.error) {
      window.alert(data.error);
      return;
    }
    window.location.href = '/';
  };

  return (
    <>
      <Div>
        <input
          required
          className="input"
          value={formData.userId}
          onChange={(e) =>
            setFormData((oldFormData) => ({
              ...oldFormData,
              userId: e.target.value,
            }))
          }
          type="text"
          placeholder=" 아이디"
        />
        <input
          required
          className="input"
          value={formData.password}
          onChange={(e) =>
            setFormData((oldFormData) => ({
              ...oldFormData,
              password: e.target.value,
            }))
          }
          type="password"
          placeholder=" 비밀번호"
        />
        <form className="checkForms">
          <div className="checkForm">
            <input type="checkbox" name="checkId" />
            <label htmlFor="checkId">아이디 저장</label>
          </div>
          <div className="checkForm">
            <input type="checkbox" name="checkOUserOrder" />
            <label htmlFor="checkLogin">자동 로그인</label>
          </div>
        </form>
      </Div>
      <Footer>
        <Button text="로그인" onClick={() => handleLogin(formData)} />
        <div className="buttons">
          <button type="button">아이디 찾기</button>
          <span />
          <button type="button">비밀번호 찾기</button>
        </div>
        <Button
          text="회원 가입"
          onClick={() => {
            window.location.href = '/join';
          }}
        />
      </Footer>
    </>
  );
};

export default Login;
