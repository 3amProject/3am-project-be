import customAxios from './customAxios';

// 홈 메뉴 받아오기
export const getMenus = async () => {
  try {
    const res = await customAxios.get('/');
    return await res.data.data;
  } catch (error) {
    return error;
  }
};

// 장바구니 수량 추가
export const plusProductQty = async (productSeq) => {
  try {
    const res = await customAxios.put(`/cart/product/plus/${productSeq}`);
    return res;
  } catch (error) {
    return error;
  }
};

// 장바구니 수량 차감
export const minusProductQty = async (productSeq) => {
  try {
    const res = await customAxios.put(`/cart/product/minus/${productSeq}`);
    return res;
  } catch (error) {
    return error;
  }
};

// 장바구니 개별 상품 삭제
export const deleteProduct = async (cartSeq) => {
  try {
    const res = await customAxios.delete(`/cart/delete/${cartSeq}`);
    return res;
  } catch (error) {
    return error;
  }
};

// 장바구니 전체 삭제(초기화)
export const deleteAllProduct = async () => {
  try {
    const res = await customAxios.delete('/cart/deleteAll');
    return res;
  } catch (error) {
    return error;
  }
};
