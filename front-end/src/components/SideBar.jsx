import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import styled, { css } from 'styled-components';

import Button from './Button/Button';

const Section = styled.section`
  z-index: 100;
  box-shadow: rgba(0, 0, 0, 0.04) 0px 3px 5px;
  ${(props) =>
    props.isOpen
      ? css`
          position: absolute;
          left: 0;
          height: 100%;
          width: 20em;
          display: flex;
          flex-direction: column;
          align-items: center;
          background-color: white;
          cursor: pointer;
        `
      : css`
          display: none;
        `}
`;
const CloseBtn = styled.button`
  width: 100%;
  height: 48px;
  font-size: 22px;
  padding: 15px;
  margin-bottom: 15px;
  display: flex;
  justify-content: flex-end;
  transition: all 0.3s;
  &:hover {
    color: var(--green);
  }
`;
const Div = styled.div`
  width: 80%;
  display: flex;
  & button {
    flex: 1 1;
    margin: 0 10px;
  }
`;
const Ul = styled.ul`
  width: 70%;
  & li {
    width: 100%;
    height: 20px;
    margin-top: 50px;
    padding-top: 15px;
    color: gray;
    border-top: 1.5px solid var(--gray);
    transition: all 0.3s;
    &: hover {
      font-weight: 700;
      color: black;
      border-top: 1.5px solid black;
      cursor: pointer;
    }
  }
`;

const SideBar = ({ onClick, isOpen }) => {
  return (
    <Section isOpen={isOpen}>
      <CloseBtn onClick={onClick}>
        <i className="fas fa-times" />
      </CloseBtn>
      <Div>
        <Button
          text="회원 가입"
          onClick={() => {
            window.location.href = '/join';
          }}
        />
        <Button
          text="로그인"
          onClick={() => {
            window.location.href = '/login';
          }}
        />
      </Div>
      <Ul>
        <Link to="/">
          <li>메뉴</li>
        </Link>
        <li>고객센터</li>
      </Ul>
    </Section>
  );
};

SideBar.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
};

export default SideBar;
