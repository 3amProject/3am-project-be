import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import { putInCart } from '../service/authService';

const Div = styled.div`
  width: 100vw;
  max-width: 1000px;
  box-sizing: border-box;
  border-top: 1px solid black;
  & div {
    padding-left: 30px;
  }
  & button {
    font-size: 16px;
    color: var(--lightbeige);
    padding: 15px 30px;
    background-color: black;
    cursor: pointer;
    transition: all 0.3s;
    &:hover {
      font-weight: 700;
      background-color: var(--green);
    }
  }
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const TotalPrice = ({ authUser, totalPrice, showPrice, date, selected }) => {
  const formatDate = (date) => {
    return date
      .toLocaleDateString('ko', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      })
      .replace(/(.\s)/g, '-')
      .replace(/\.$/, '');
  };
  const handlePutInCart = async () => {
    if (!date) {
      window.alert('날짜를 선택해주세요.');
      return;
    }
    if (selected.length < 1) {
      window.alert('상품을 선택해주세요.');
      return;
    }
    const newDate = formatDate(date);
    selected.forEach((v) => {
      // 각 obj에 date 삽입
      v.deliveryDate = newDate;
    });
    const cartList = await putInCart(selected);
    if (cartList && cartList?.data?.code === 'SC003') {
      window.alert(cartList.data.message);
    }
  };

  return (
    <Div>
      <div>
        <span>결제금액 </span>
        <span>총 {showPrice(totalPrice)}</span>
      </div>
      <button
        type="button"
        onClick={() =>
          authUser ? handlePutInCart() : (window.location.href = '/login')
        }
      >
        장바구니에 담기
      </button>
    </Div>
  );
};

TotalPrice.propTypes = {
  authUser: PropTypes.bool,
  totalPrice: PropTypes.number,
  showPrice: PropTypes.func,
  date: PropTypes.string,
  selected: PropTypes.arrayOf(PropTypes.object),
};

TotalPrice.defaultProps = {
  authUser: false,
  totalPrice: 0,
  showPrice: null,
  date: null,
  selected: [],
};

export default TotalPrice;
