import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import moment from 'moment';
import { getMenus } from './service/noAuthService';

import AuthRoute from './routes/AuthRoute';
import Home from './routes/Home/Home';
import JoinPage from './routes/Join/JoinPage';
import LoginPage from './routes/Login/LoginPage';
import ProfilePage from './routes/Profile/ProfilePage';
import ProfileEditPage from './routes/ProfileEdit/ProfileEditPage';
import CartPage from './routes/Cart/CartPage';
import OrderTypePage from './routes/OrderType/OrderTypePage';
import UserOrderPage from './routes/UserOrder/UserOrderPage';
import NotUserOrderPage from './routes/NotUserOrder/NotUserOrderPage';

const App = () => {
  const isLoggedIn = () => {
    return !!localStorage.getItem('accessToken');
  };
  const [authUser, setAuthUser] = useState(isLoggedIn);

  const [isOpen, setIsOpen] = useState(false);
  const toggleSideBar = () => {
    setIsOpen((isOpen) => !isOpen);
  };

  const [menus, setMenus] = useState([]);

  const showPrice = (price) => {
    if (price < 1000) return `${price} 원`;
    const newPrice = String(price).split('').reverse();
    newPrice.splice(3, 0, ',');
    return `${newPrice.reverse().join('')} 원`;
  };
  const formatDate = (date) => {
    return moment(date)
      .add(9, 'hours')
      .format('YYYY' - 'MM' - 'DD')
      .slice(0, 10);
  };
  useEffect(() => {
    setAuthUser(() => isLoggedIn());
  }, [authUser]);

  useEffect(() => {
    getMenus()
      .then((result) => setMenus(result))
      .catch((error) => console.log(error));
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <Home
              authUser={authUser}
              onClick={toggleSideBar}
              isOpen={isOpen}
              menus={menus}
              showPrice={showPrice}
            />
          }
        />

        <Route
          path="/join"
          element={<JoinPage onClick={toggleSideBar} isOpen={isOpen} />}
        />

        <Route
          path="/profile"
          element={
            <AuthRoute
              authUser={authUser}
              element={
                <ProfilePage
                  onClick={toggleSideBar}
                  isOpen={isOpen}
                  showPrice={showPrice}
                  formatDate={formatDate}
                />
              }
            />
          }
        />

        <Route
          path="/profileEdit"
          element={
            <AuthRoute
              authUser={authUser}
              element={
                <ProfileEditPage onClick={toggleSideBar} isOpen={isOpen} />
              }
            />
          }
        />

        <Route
          path="/cart"
          element={
            <AuthRoute
              authUser={authUser}
              element={
                <CartPage
                  onClick={toggleSideBar}
                  isOpen={isOpen}
                  formatDate={formatDate}
                />
              }
            />
          }
        />

        <Route
          path="/userOrder"
          element={
            <AuthRoute
              authUser={authUser}
              element={
                <UserOrderPage
                  onClick={toggleSideBar}
                  isOpen={isOpen}
                  showPrice={showPrice}
                  formatDate={formatDate}
                />
              }
            />
          }
        />

        <Route
          path="/notUserOrder"
          element={<NotUserOrderPage onClick={toggleSideBar} isOpen={isOpen} />}
        />

        <Route path="/login" element={<LoginPage />} />

        <Route
          path="/orderType"
          element={<OrderTypePage authUser={authUser} />}
        />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
