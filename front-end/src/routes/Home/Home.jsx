import React, { memo, useState } from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import ReactCalendar from '../../components/Calendar';
import Footer from '../../components/Footer';
import Header from '../../components/Header';
import MenuContainer from './MenuContainer';
import SideBar from '../../components/SideBar';
import TotalPrice from '../../components/TotalPrice';

const Section = styled.section`
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  background-color: white;
`;
const Main = styled.main`
  width: 90vw;
  max-width: 700px;
  padding: 20px;
  box-sizing: border-box;
  overflow: scroll;
`;
const Div = styled.div`
  margin-top: 50px;
  & .title {
    font-size: 20px;
  }
`;
const Calendar = styled.div`
  display: flex;
  justify-content: center;
  padding-top: 50px;
`;

const Home = memo(({ authUser, onClick, isOpen, menus, showPrice }) => {
  console.log('menus', menus)
  const salads = menus?.slice(0, 9);
  const convenFoods = menus?.slice(9);
  const [totalPrice, setTotalPrice] = useState(0);
  const [selected, setSelected] = useState([]);
  const [date, setDate] = useState();
  const handleAdd = (menu) => {
    const clickedMenu = { productSeq: menu.id, productQty: menu.productQty };
    setTotalPrice((oldPrice) => +oldPrice + +menu.productPrice);
    setSelected((oldSelected) => {
      const updated = [...oldSelected];
      const target = updated.findIndex(
        (v) => v.productSeq === clickedMenu.productSeq
      );
      if (target === -1) {
        updated.push({ ...clickedMenu, productQty: 1 });
      } else {
        updated[target].productQty += 1;
      }
      return updated;
    });
  };

  return (
    <Section>
      <Header />
      <Main>
        <Div>
          <h2 className="title">1. 배송 날짜 선택</h2>
          <Calendar>
            <ReactCalendar date={date} onChange={(date) => setDate(date)} />
          </Calendar>
        </Div>
        <Div>
          <h2 className="title">2. 메뉴 선택</h2>
          <div className="menuList">
            <MenuContainer
              subTitle="매일 구매한 신선한 재료로 당일 조리"
              title="새벽다섯시 샐러드"
              menus={salads}
              foodImgs01={[1, 2, 3, 4, 5, 6, 7, 8, 9]}
              onClick={handleAdd}
              showPrice={showPrice}
            />
            <MenuContainer
              subTitle="매일 하나씩만 받기 어려웠던"
              title="건강 간편식"
              menus={convenFoods}
              foodImgs02={[10, 11, 12, 13, 14, 15, 16, 17, 18]}
              onClick={handleAdd}
              showPrice={showPrice}
            />
          </div>
        </Div>
      </Main>
      <TotalPrice
        authUser={authUser}
        totalPrice={totalPrice}
        showPrice={showPrice}
        date={date}
        selected={selected}
      />
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
});

Home.propTypes = {
  authUser: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
  menus: PropTypes.arrayOf(PropTypes.object).isRequired,
  showPrice: PropTypes.func.isRequired,
};

export default Home;
