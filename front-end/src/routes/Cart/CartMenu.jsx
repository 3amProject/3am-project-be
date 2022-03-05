import React, { memo, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import {
  deleteProduct,
  minusProductQty,
  plusProductQty,
} from '../../service/noAuthService';

const Li = styled.li`
  width: 100%;
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  & .qtyName {
    width: 150px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
  & .qtyInput {
    font-size: 16px;
    width: 30px;
    border: none;
    background-color: transparent;
    text-align: center;
  }
  & input[type='number']::-webkit-outer-spin-button,
  & input[type='number']::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;

const CartMenu = memo(({ id, name, qty }) => {
  const [productQty, setProductQty] = useState(0);

  const handleQtyMinus = async () => {
    if (productQty <= 1) {
      return;
    }
    setProductQty((old) => old - 1);
    const res = await minusProductQty(id);
    if (res && res?.data?.message) {
      window.alert(res.data.message);
    }
  };
  const handleQtyPlus = async () => {
    setProductQty((old) => old + 1);
    const res = await plusProductQty(id);
    if (res && res?.data?.message) {
      window.alert(res.data.message);
    }
  };
  const removeProdut = async () => {
    if (!window.confirm('상품을 삭제하시겠습니까?')) {
      return;
    }
    const res = await deleteProduct(id);
    if (res && res?.data?.message) {
      window.location.reload();
      window.alert(res.data.message);
    }
  };
  useEffect(() => {
    setProductQty((oldValue) => oldValue + qty);
  }, [qty]);
  return (
    <Li>
      <button type="button" onClick={() => removeProdut()}>
        <i className="fas fa-minus" />
      </button>
      <p className="qtyName">{name}</p>
      <div>
        <button type="button" onClick={() => handleQtyMinus()}>
          -
        </button>
        <input
          className="qtyInput"
          type="number"
          min="1"
          step="1"
          value={productQty}
          readOnly
        />
        <button type="button" onClick={() => handleQtyPlus()}>
          +
        </button>
      </div>
    </Li>
  );
});

CartMenu.propTypes = {
  id: PropTypes.number,
  name: PropTypes.string,
  qty: PropTypes.number,
};

CartMenu.defaultProps = {
  id: null,
  name: null,
  qty: null,
};

export default CartMenu;
