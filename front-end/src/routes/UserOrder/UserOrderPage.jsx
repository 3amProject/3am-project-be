import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import { getOrderPage, postOrder } from '../../service/authService';

import Loading from '../../components/Loading';
import UserOrder from '../../components/UserOrder';
import Header from '../../components/Header';
import Footer from '../../components/Footer';
import SideBar from '../../components/SideBar';
import Button from '../../components/Button/Button';

const Section = styled.section`
  height: 100vh;
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: white;
`;
const Main = styled.main`
  width: 90vw;
  padding: 0 5vw;
  overflow: scroll;
  max-width: 500px;
  & .title {
    margin: 50px 0;
  }
  & .text {
    margin: 20px 0;
    font-weight: 700;
  }
  & .orderInfo {
    background-color: var(--lightgray);
    padding: 20px;
    border-radius: 10px;
  }
  & .userInfos {
    background-color: var(--lightgray);
    padding: 20px;
    border-radius: 10px;
    & .userInfo {
      display: flex;
      align-items: center;
      & .userTitle {
        width: 100px;
        box-sizing: border-box;
        padding: 5px 0px;
        margin-right: 50px;
        font-weight: 700;
      }
    }
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

const UserOrderPage = ({ onClick, isOpen, showPrice, formatDate }) => {
  const [userOrderInfo, setUserOrderInfo] = useState();
  const [loading, setLoading] = useState(true);

  const getTotalQty = () => {
    const list = userOrderInfo.cartList.map((v) => v.productQty);
    const sum = list.reduce((prev, curr) => prev + curr);
    return sum;
  };

  const getTotalPrice = () => {
    const list = userOrderInfo.cartList.map((v) => v.totalPrice);
    const sum = list.reduce((prev, curr) => prev + curr);
    return sum;
  };

  const handleOrder = async () => {
    if (!window.confirm('결제를 진행하시겠습니까?')) {
      alert('결제를 취소하였습니다');
      return;
    }
    const { name, phoneNum, address, email } = userOrderInfo.userInfo;
    const { deliveryDate } = userOrderInfo.cartList[0];
    const { cartList } = userOrderInfo;
    const orderDetails = cartList.map((v) => {
      const obj = {};
      obj.productSeq = v.productSeq;
      obj.productQty = v.productQty;
      return obj;
    });
    const res = await postOrder({
      username: name,
      address,
      phoneNum,
      email,
      deliveryDate: formatDate(deliveryDate),
      orderDetails,
    });
    if (res) {
      window.alert('주문이 완료되었습니다 🥳');
      window.location.href = '/';
    }
  };

  useEffect(() => {
    getOrderPage().then((result) => setUserOrderInfo(result));
    setTimeout(() => {
      setLoading(false);
    }, 500);
  }, []);

  if (loading) {
    return <Loading />;
  }

  return (
    <Section>
      <Header />
      <Main>
        <h1 className="title">1. 주문 상품 정보</h1>
        <p className="text">
          배송 날짜 : {formatDate(userOrderInfo.cartList[0].deliveryDate)}
        </p>
        <ul className="orderInfo">
          {userOrderInfo.cartList.length > 1 ? (
            userOrderInfo.cartList.map((v) => {
              const { id, productName, productQty, totalPrice } = v;
              return (
                <UserOrder
                  key={id}
                  name={productName}
                  qty={productQty}
                  totalPrice={showPrice(totalPrice)}
                />
              );
            })
          ) : (
            <UserOrder
              name={userOrderInfo.cartList[0].productName}
              qty={userOrderInfo.cartList[0].productQty}
              totalPrice={showPrice(userOrderInfo.cartList[0].totalPrice)}
            />
          )}
        </ul>
        <h1 className="title">2. 주문자 정보</h1>
        <ul className="userInfos">
          <li className="userInfo">
            <p className="userTitle">이름 : </p>
            <p>{userOrderInfo.userInfo.name}</p>
          </li>
          <li className="userInfo">
            <p className="userTitle">주소 : </p>
            <p>{userOrderInfo.userInfo.address}</p>
          </li>
          <li className="userInfo">
            <p className="userTitle">전화번호 : </p>
            <p>{userOrderInfo.userInfo.phoneNum}</p>
          </li>
          <li className="userInfo">
            <p className="userTitle">이메일 : </p>
            <p>{userOrderInfo.userInfo.email}</p>
          </li>
        </ul>
        <h1 className="title">3. 결제 정보</h1>
        <p className="text">총 수량 : {getTotalQty()}개</p>
        <p className="text">총 가격 : {showPrice(getTotalPrice())}</p>
        <div className="buttons">
          <Button onClick={() => handleOrder()} text="결제하기" />
          <Button
            text="취소"
            onClick={() => {
              window.location.href = '/';
            }}
          />
        </div>
      </Main>
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
};

UserOrderPage.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
  showPrice: PropTypes.func.isRequired,
  formatDate: PropTypes.func.isRequired,
};

export default UserOrderPage;
