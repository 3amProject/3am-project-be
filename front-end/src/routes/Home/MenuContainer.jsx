import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Menu from './Menu';

const Section = styled.section`
  padding: 50px 0;
  box-sizing: border-box;
  & .title {
    font-size: 20px;
    font-weight: 700;
    padding-top: 10px;
  }
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Menus = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;
  margin-top: 50px;
  @media screen and (max-width: 64rem) {
    grid-template-columns: 1fr 1fr;
  }
`;

const MenuContainer = ({
  subTitle,
  title,
  menus,
  onClick,
  showPrice,
  foodImgs01,
  foodImgs02,
}) => {
  return (
    <Section>
      <span>{subTitle}</span>
      <span className="title">{title}</span>
      <Menus>
        {menus.map((menu, idx) => (
          <Menu
            key={menu.id}
            menu={menu}
            imgUrl={
              foodImgs01
                ? `/img/${foodImgs01[idx]}.png`
                : `/img/${foodImgs02[idx]}.png`
            }
            onClick={onClick}
            showPrice={showPrice}
          />
        ))}
      </Menus>
    </Section>
  );
};

MenuContainer.propTypes = {
  subTitle: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  menus: PropTypes.arrayOf(PropTypes.object).isRequired,
  onClick: PropTypes.func.isRequired,
  showPrice: PropTypes.func.isRequired,
  foodImgs01: PropTypes.arrayOf(PropTypes.number),
  foodImgs02: PropTypes.arrayOf(PropTypes.number),
};

MenuContainer.defaultProps = {
  foodImgs01: null,
  foodImgs02: null,
};

export default MenuContainer;
