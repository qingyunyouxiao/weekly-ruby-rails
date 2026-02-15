=begin
# 定义file_list action
# Dir.glob('*') 获取当前目录下所有文件/文件夹名称，返回字符串数组
# 赋值给实例变量 @files，这样视图就能访问到这个变量
# 指定具体目录，可以写绝对路径
=end
class FilesController < ApplicationController
  def file_list
    all_items = Dir.glob(Rails.root.join("*"))
    @files = all_items.map { |item| File.basename(item) }
    Rails.logger.info "file_list #{@files.inspect}"

    render :file_list
  end
end
