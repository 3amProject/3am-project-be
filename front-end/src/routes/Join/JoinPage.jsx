import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Footer from '../../components/Footer';
import Header from '../../components/Header';
import Join from './Join';
import SideBar from '../../components/SideBar';

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

const JoinPage = ({ onClick, isOpen }) => {
  return (
    <Section>
      <Header />
      <Main>
        <Join />
      </Main>
      <Footer onClick={onClick} isOpen={isOpen} />
      <SideBar onClick={onClick} isOpen={isOpen} />
    </Section>
  );
};

JoinPage.propTypes = {
  onClick: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
};

export default JoinPage;
