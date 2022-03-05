import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Header from '../../components/Header';
import Footer from '../../components/Footer';
import SideBar from '../../components/SideBar';
import Profile from './Profile';

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
  max-width: 500px;
  overflow: scroll;
  @media screen and (max-width: 64rem) {
    box-sizing: border-box;
  }
`;

const ProfilePage = ({ onClick, isOpen, showPrice, formatDate }) => {
  return (
    <Section>
      <Header />
      <Main>
        <Profile showPrice={showPrice} formatDate={formatDate} />
      </Main>
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
};

ProfilePage.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
  showPrice: PropTypes.func.isRequired,
  formatDate: PropTypes.func.isRequired,
};

export default ProfilePage;
