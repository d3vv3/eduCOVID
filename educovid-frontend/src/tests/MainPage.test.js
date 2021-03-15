import { render, screen } from '@testing-library/react';
import MainPage from '../pages/MainPage';

test('App name appears on the main page', () => {
  render(<MainPage />);
  const name = screen.getByText(/eduCOVID/i);
  expect(name).toBeInTheDocument();
});
