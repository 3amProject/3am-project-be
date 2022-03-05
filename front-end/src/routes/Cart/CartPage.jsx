import styled from 'styled-components';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

import { getCart } from '../../service/authService';
import { deleteAllProduct } from '../../service/noAuthService';

import Footer from '../../components/Footer';
import Header from '../../components/Header';
import SideBar from '../../components/SideBar';
import Loading from '../../components/Loading';
import CartMenus from './CartMenus';
import Button from '../../components/Button/Button';

const Section = styled.section`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: white;
`;
const Main = styled.main`
  width: 90vw;
  padding: 0 5vw;
  max-width: 500px;
  overflow: scroll;
  & .emptyCart {
    text-align: center;
  }
  & .title {
    margin: 50px 0;
  }
  & .buttons {
    margin-bottom: 50px;
    display: flex;
    flex-direction: column;
  }
  @media screen and (max-width: 64rem) {
    box-sizing: border-box;
  }
`;

const CartPage = ({ onClick, isOpen, formatDate }) => {
  const [loading, setLoading] = useState(true);
  const [cartItems, setCartItems] = useState([]);
  const [deliveryDate, setDeliveryDate] = useState();

  const removeAllProdut = async () => {
    if (!window.confirm('장바구니를 비우시겠습니까?')) {
      return;
    }
    const res = await deleteAllProduct();
    if (res && res?.data?.message) {
      window.location.reload();
      window.alert(res.data.message);
    }
  };
  useEffect(() => {
    getCart().then((res) => {
      const obj = res[0] || {};
      const { deliveryDate = '' } = obj;
      setCartItems(res);
      setDeliveryDate(formatDate(deliveryDate));
      setTimeout(() => {
        setLoading(false);
      }, 500);
    });
  }, [ formatDate ]);

  if (loading) {
    return <Loading />;
  }
  return (
    <Section>
      <Header />
      <Main>
        {cartItems.length === 0 ? (
          <h1 className="emptyCart">장바구니가 비었습니다.</h1>
        ) : (
          <>
            <h1 className="title">장바구니 목록</h1>
            <div className="menus">
              <CartMenus cartItems={cartItems} date={deliveryDate} />
            </div>
            <div className="buttons">
              <Button
                text="장바구니 비우기"
                onClick={() => removeAllProdut()}
              />
              <Button
                text="주문하기"
                onClick={() => {
                  window.location.href = '/orderType';
                }}
              />
            </div>
          </>
        )}
      </Main>
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
};

CartPage.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
  formatDate: PropTypes.func.isRequired,
};

export default CartPage;
