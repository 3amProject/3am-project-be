import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import { signOut, getProfile } from '../../service/authService';

import ProfileOrders from './ProfileOrders';
import Loading from '../../components/Loading';
import Button from '../../components/Button/Button';

const Section = styled.section`
  display: flex;
  flex-direction: column;
  overflow: scroll;
  & .title {
    margin: 50px 0;
  }
  & .subTitle {
    margin: 20px 0px;
    font-size: 18px;
  }
  & .buttons {
    margin: 50px 0;
    display: flex;
    flex-direction: column;
  }
`;
const Ul = styled.ul`
  margin: 50px 0;
  & li {
    margin-bottom: 10px;
    margin-left: 20px;
    display: flex;
    align-items: center;
    & .userInfoTitle {
      width: 100px;
      box-sizing: border-box;
      padding: 5px 0px 5px 20px;
      font-weight: 700;
    }
  }
`;

const Profile = ({ showPrice, formatDate }) => {
  const [userData, setUserData] = useState();
  const handleLogout = () => {
    signOut();
    window.location.href = '/login';
  };

  useEffect(() => {
    getProfile()
      .then((result) => {
        setUserData(result);
      })
      .catch((error) => console.log(error));
  }, []);

  return userData ? (
    <Section>
      <h1 className="title">마이 페이지</h1>
      <h2 className="subTitle">회원 정보</h2>
      <Ul>
        <li>
          <span className="userInfoTitle">이름</span>
          <span>{userData.userInfo.name}</span>
        </li>
        <li>
          <span className="userInfoTitle">아이디</span>
          <span>{userData.userInfo.userId}</span>
        </li>
        <li>
          <span className="userInfoTitle">이메일</span>
          <span>{userData.userInfo.email}</span>
        </li>
        <li>
          <span className="userInfoTitle">주소</span>
          <span>{userData.userInfo.address}</span>
        </li>
        <li>
          <span className="userInfoTitle">전화번호</span>
          <span>{userData.userInfo.phoneNum}</span>
        </li>
      </Ul>
      <h2 className="subTitle">주문 내역</h2>
      {userData.orderHistory.length > 0 ? (
        userData.orderHistory.length >= 1 ? (
          userData.orderHistory.map((v) => {
            const { id, deliveryDate, orderTotalPrice, orderDetails } = v;
            return (
              <ProfileOrders
                key={id}
                date={formatDate(deliveryDate)}
                totalPrice={showPrice(orderTotalPrice)}
                orderDetails={orderDetails}
                showPrice={showPrice}
              />
            );
          })
        ) : (
          <ProfileOrders
            date={formatDate(userData.orderHistory[0].deliveryDate)}
            totalPrice={showPrice(userData.orderHistory[0].orderTotalPrice)}
            orderDetails={userData.orderHistory[0].orderDetails}
            showPrice={showPrice}
          />
        )
      ) : (
        <p />
      )}
      <div className="buttons">
        <Button
          text="회원 정보 수정"
          onClick={() => {
            window.location.href = '/profileEdit';
          }}
        />
        <Button text="로그아웃" onClick={() => handleLogout()} />
      </div>
    </Section>
  ) : (
    <Loading />
  );
};

Profile.propTypes = {
  showPrice: PropTypes.func.isRequired,
  formatDate: PropTypes.func.isRequired,
};

export default Profile;
