import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import CartMenu from './CartMenu';

const Div = styled.div`
  padding: 30px;
  margin-bottom: 50px;
  background-color: var(--lightgray);
  border-radius: 10px;
  box-sizing: border-box;
  & .date {
    margin: 10px;
    font-weight: 700;
  }
  & .menus {
    margin: 10px;
  }
  & ul {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  @media screen and (max-width: 64rem) {
    padding: 10px;
  }
`;

const CartMenus = ({ cartItems, date }) => {
  if (cartItems.length === 0) {
    return null;
  }
  return cartItems.length > 1 ? (
    <Div>
      <p className="date">배송 날짜 : {date}</p>
      <details className="menus" open>
        <summary>메뉴</summary>
        <ul>
          {cartItems.map((menu) => {
            const { id, productName, productQty } = menu;
            return (
              <CartMenu key={id} id={id} name={productName} qty={productQty} />
            );
          })}
        </ul>
      </details>
    </Div>
  ) : (
    <Div>
      <p className="date">배송 날짜 : {date}</p>
      <details className="menus" open>
        <summary>메뉴</summary>
        <ul>
          <CartMenu
            key={cartItems[0].id}
            id={cartItems[0].id}
            name={cartItems[0].productName}
            qty={cartItems[0].productQty}
          />
        </ul>
      </details>
    </Div>
  );
};

CartMenus.propTypes = {
  cartItems: PropTypes.arrayOf(PropTypes.object),
  date: PropTypes.string,
};

CartMenus.defaultProps = {
  cartItems: [],
  date: '',
};

export default CartMenus;
