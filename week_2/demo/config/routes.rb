Rails.application.routes.draw do
  get "up" => "rails/health#show", as: :rails_health_check
  get "file_list", to: "files#file_list"
end
