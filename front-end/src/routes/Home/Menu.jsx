import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

const Div = styled.div`
  border: 1px solid var(--lightgray);
  padding: 16px;
  & .name {
    font-size: 16px;
    margin-top: 20px;
    height: 50px;
    line-height: 20px;
  }
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  @media screen and (max-width: 64rem) {
    & .name {
      margin-top: 30px;
      height: 70px;
    }
  }
`;
const Footer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;
const Button = styled.button`
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background-color: black;
  color: white;
  font-size: 16px;
  transition: all 0.3s;
  &:hover {
    transform: scale(1.1);
    background-color: var(--green);
  }
`;

const Menu = ({ menu, onClick, showPrice, imgUrl }) => {
  return (
    <Div>
      <img src={imgUrl} alt="img" />
      <p className="name">{menu.productName}</p>
      <Footer>
        <span>{showPrice(menu.productPrice)}</span>
        <Button onClick={() => onClick(menu)}>
          <i className="fas fa-shopping-basket" />
        </Button>
      </Footer>
    </Div>
  );
};

Menu.propTypes = {
  menu: PropTypes.shape({
    id: PropTypes.number,
    productName: PropTypes.string,
    productPrice: PropTypes.string,
    productQty: PropTypes.string,
    category1: PropTypes.string,
    category2: PropTypes.string,
  }).isRequired,
  onClick: PropTypes.func.isRequired,
  showPrice: PropTypes.func.isRequired,
  imgUrl: PropTypes.string.isRequired,
};

export default Menu;
