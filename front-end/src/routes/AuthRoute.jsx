import React from 'react';
import PropTypes from 'prop-types';
import { Navigate } from 'react-router-dom';

const AuthRoute = ({ authUser, element }) => {
  return authUser ? element : <Navigate replace to="/login" />;
};

AuthRoute.propTypes = {
  authUser: PropTypes.bool.isRequired,
  element: PropTypes.node.isRequired,
};

export default AuthRoute;
