import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import UserOrder from '../../components/UserOrder';

const Div = styled.div`
  & .date {
    font-weight: 700;
    margin-top: 50px;
  }
  & .orderTitle {
    margin: 10px;
  }
  & .order {
    padding: 20px 10px;
    margin-bottom: 10px;
    border-radius: 10px;
    background-color: var(--lightgray);
  }
  & .totalPrice {
    text-align: end;
  }
`;

const ProfileOrders = ({ date, totalPrice, orderDetails, showPrice }) => {
  return (
    <Div>
      <p className="date">배송 날짜 : {date}</p>
      <details>
        <summary className="orderTitle">메뉴</summary>
        <ul className="order">
          {orderDetails.length > 1 ? (
            orderDetails.map((v) => {
              const { productSeq, productName, productQty, totalPrice } = v;
              return (
                <UserOrder
                  key={productSeq}
                  name={productName}
                  qty={productQty}
                  totalPrice={showPrice(totalPrice)}
                />
              );
            })
          ) : (
            <UserOrder
              name={orderDetails[0].productName}
              qty={orderDetails[0].productQty}
              totalPrice={showPrice(orderDetails[0].totalPrice)}
            />
          )}
        </ul>
      </details>
      <p className="totalPrice">총 가격 : {totalPrice}</p>
    </Div>
  );
};

ProfileOrders.propTypes = {
  date: PropTypes.string.isRequired,
  totalPrice: PropTypes.string.isRequired,
  orderDetails: PropTypes.arrayOf(PropTypes.object).isRequired,
  showPrice: PropTypes.func.isRequired,
};

export default ProfileOrders;
