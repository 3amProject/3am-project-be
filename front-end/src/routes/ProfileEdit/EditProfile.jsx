import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import {
  putProfileEdit,
  signOut,
  getProfileEdit,
} from '../../service/authService';

import Button from '../../components/Button/Button';

const Section = styled.section`
  display: flex;
  flex-direction: column;
  & .title {
    margin: 50px 0;
  }
  & .buttons {
    margin-bottom: 50px;
    display: flex;
    flex-direction: column;
  }
`;
const Form = styled.form`
  display: flex;
  flex-direction: column;
  margin-bottom: 120px;
  & .input {
    display: flex;
    & label {
      width: 100px;
      box-sizing: border-box;
      padding: 5px 0px 5px 20px;
      color: gray;
      font-weight: 700;
    }
  }
`;
const Input = styled.input`
  margin-bottom: 10px;
  width: 60%;
  padding: 5px 0 5px 10px;
  font-size: 16px;
  border: 1.5px solid var(--gray);
  border-radius: 10px;
`;

const EditProfile = () => {
  const [formData, setFormData] = useState({
    name: '',
    // password: '',
    email: '',
    address: '',
    phoneNum: '',
  });

  const handleLogout = () => {
    signOut();
    window.location.href = '/login';
  };

  const handleEdit = async (formData) => {
    if (!window.confirm('회원 정보를 수정하시겠습니까?')) {
      return;
    }
    const res = await putProfileEdit(formData, [
      'name',
      'email',
      'address',
      'phoneNum',
    ]);
    if (res) {
      console.log(res);
      if (res.error) {
        window.alert(res.error);
      } else {
        window.alert('회원 정보 수정 완료 👍');
      }
    }
  };

  useEffect(() => {
    getProfileEdit()
      .then((result) => setFormData(result))
      .catch((error) => console.log(error));
  }, []);

  return (
    <Section>
      <h1 className="title">회원 정보 수정</h1>
      <Form>
        <div className="input">
          <label htmlFor="name">이름</label>
          <Input
            required
            id="name"
            value={formData.name}
            onChange={(e) =>
              setFormData((oldFormData) => ({
                ...oldFormData,
                name: e.target.value,
              }))
            }
            type="text"
            placeholder={formData.name}
          />
        </div>
        <div className="input">
          <label htmlFor="email">이메일</label>
          <Input
            required
            id="email"
            value={formData.email}
            onChange={(e) =>
              setFormData((oldFormData) => ({
                ...oldFormData,
                email: e.target.value,
              }))
            }
            type="email"
            placeholder={formData.email}
          />
        </div>
        <div className="input">
          <label htmlFor="address">주소</label>
          <Input
            required
            id="address"
            value={formData.address}
            onChange={(e) =>
              setFormData((oldFormData) => ({
                ...oldFormData,
                address: e.target.value,
              }))
            }
            type="text"
            placeholder={formData.address}
          />
        </div>
        <div className="input">
          <label htmlFor="phoneNum">전화번호</label>
          <Input
            required
            id="phoneNum"
            value={formData.phoneNum}
            onChange={(e) =>
              setFormData((oldFormData) => ({
                ...oldFormData,
                phoneNum: e.target.value,
              }))
            }
            type="tel"
            placeholder={formData.phoneNum}
          />
        </div>
        {/* <Input 
        required
        value={formData.password}
        onChange={(e) => setFormData(oldFormData => ({...oldFormData, password: e.target.value}))}
        type="password" placeholder={formData.password} /> */}
      </Form>
      <div className="buttons">
        <Button text="저장" onClick={() => handleEdit(formData)} />
        <Button text="로그아웃" onClick={() => handleLogout()} />
      </div>
    </Section>
  );
};

export default EditProfile;
