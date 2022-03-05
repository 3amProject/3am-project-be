import React from 'react';
import styled from 'styled-components';

import Button from '../../components/Button/Button';

const Div = styled.div`
  margin: 30px 0;
  display: flex;
  flex-direction: column;
  & input {
    padding: 5px 0 5px 10px;
    margin-bottom: 10px;
    font-size: 16px;
    border: 1.5px solid var(--gray);
    border-radius: 10px;
  }
`;

const NotUserLogin = () => {
  return (
    <Div>
      <input type="text" placeholder=" 주문번호" />
      <input type="text" placeholder=" 주문자명" />
      <Button text="확인" />
    </Div>
  );
};

export default NotUserLogin;
