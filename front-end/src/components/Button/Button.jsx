import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

const Btn = styled.button`
  padding: 16px 0;
  margin: 10px;
  font-size: 14px;
  border-radius: 10px;
  border: 1.5px solid black;
  position: relative;
  overflow: hidden;
  transition: all 0.3s;
  z-index: 1;
  &:after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -2;
  }
  &:before {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 0%;
    background-color: black;
    transition: all 0.3s;
    z-index: -1;
  }
  &:hover {
    color: var(--lightbeige);
    font-weight: 700;
    &:before {
      height: 100%;
    }
  }
`;

const Button = ({ text, onClick }) => {
  return <Btn onClick={onClick}>{text}</Btn>;
};

Button.propTypes = {
  text: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default Button;
