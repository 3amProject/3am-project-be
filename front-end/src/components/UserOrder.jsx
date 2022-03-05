import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

const Li = styled.li`
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  & .name {
    width: 150px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
  @media screen and (max-width: 64rem) {
    & .name {
      width: 120px;
    }
  }
`;

const UserOrder = ({ name, qty, totalPrice }) => {
  return (
    <Li>
      <p className="name">{name}</p>
      <p className="qty">{qty}</p>
      <p className="price">{totalPrice}</p>
    </Li>
  );
};

UserOrder.propTypes = {
  name: PropTypes.string.isRequired,
  qty: PropTypes.number.isRequired,
  totalPrice: PropTypes.string.isRequired,
};

export default UserOrder;
