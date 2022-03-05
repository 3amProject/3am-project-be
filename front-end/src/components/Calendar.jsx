import React from 'react';
import PropTypes from 'prop-types';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

const ReactCalendar = ({ date, onChange }) => {
  const now = Date.now();
  const deliveryDate = now + 1000 * 60 * 60 * 24 * 2;

  return (
    <Calendar
      onChange={onChange}
      value={date}
      defaultValue={null}
      calendarType="Hebrew"
      prev2Label={null}
      next2Label={null}
      minDate={new Date(deliveryDate)}
    />
  );
};

ReactCalendar.propTypes = {
  date: PropTypes.string,
  onChange: PropTypes.func,
};

ReactCalendar.defaultProps = {
  date: null,
  onChange: null,
};

export default ReactCalendar;
